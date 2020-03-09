// https://mvnrepository.com/artifact/org.codehaus.groovy/groovy-cli-picocli
@Grapes(
    @Grab(group='org.codehaus.groovy', module='groovy-cli-picocli', version='2.5.10')
)
import groovy.cli.picocli.CliBuilder
import groovy.json.JsonSlurper
import groovy.json.JsonOutput

def cli = new CliBuilder()
cli.name = "g-alot"
cli.usageMessage.with {                
    header("""@|bold,fg(green) \

    _______         _______  ___      _______  _______ 
   |       |       |   _   ||   |    |       ||       |
   |    ___| ____  |  |_|  ||   |    |   _   ||_     _|
   |   | __ |____| |       ||   |    |  | |  |  |   |  
   |   ||  |       |       ||   |___ |  |_|  |  |   |  
   |   |_| |       |   _   ||       ||       |  |   |  
   |_______|       |__| |__||_______||_______|  |___|
                                       generate a lot 
|@""")     
    synopsisHeading("%n@|underline USAGE:|@ ")
    optionListHeading("%n@|underline OPTIONS:|@%n")
}
cli.h(longOpt: "help", "Usage Information", required: false)
cli.v(longOpt: "verbose", "Print verbose output", required: false)
cli.H(longOpt: "home", "Define the g-alot home path", required: false, type: String.class)

cli.l(longOpt: "list", "List available Templates", required: false)
cli.f(longOpt: "find", "filters output of -l, --list", required: false, type: String.class)
cli.t(longOpt: "template", "Print Template", required: false, type: String.class)
cli.p(longOpt: "placeholders", "Print available placeholders in Template", required: false)
cli.r(longOpt: "replace", "Replace placeholders with ...", required: false, args: org.apache.commons.cli.Option.UNLIMITED_VALUES, valueSeparator: ',')

cli.toc("Print a Table of Content by a .md file", required: false, type: String.class)   
cli.table("Print a .md table by tablenames", required: false, args: org.apache.commons.cli.Option.UNLIMITED_VALUES, valueSeparator: ',')
cli.mdpb(longOpt: "md-page-break", "Print a .md pagebreak", required: false, type: Boolean.class)


cli.i(longOpt: "index-templates", "Reindexing Templates", required: false)
cli.c(longOpt: "copy-clip", "Copies --template in to clipboard\n(keep shell open until pasted)", required: false)

def cliArgs = cli.parse(args)
if (cliArgs.v) 
    println "[+] Start"

gAlot_Home = (cliArgs?.home ?: new File("").absolutePath).replaceAll("/\$", "")
if (cliArgs.v) 
    println "[+] G-ALOT Home : ${gAlot_Home}"
def cache = gAlot_Home + "/template-cache.json"
def cacheFile = new File(cache)
if (cliArgs.v) 
    println "[+] G-ALOT Cache: ${cache}"
    


Map<String, Map> commandMap = new TreeMap()
if (cacheFile.exists())
    commandMap = new JsonSlurper().parse(cacheFile)

if (commandMap.isEmpty() || cliArgs.i) {

    println "[+] Indexing templates in: ${gAlot_Home}"
    File fileDir = new File(gAlot_Home)
    fileDir.eachDirRecurse() { dir ->  
        dir.eachFile { file ->
            if (!file.isDirectory() && !file.parentFile.name.equals("hide") && !(file.toString() =~ /.info$/)) {
                String key = "${file.parentFile.name}-${file.name}"
                commandMap.put(key, [path : file.absolutePath]) 
                if (cliArgs.v)
                    println "[+] Template added indexed:\n\tKey : ${key}\n\tFile: ${file}"
            }
        }  
    }

    def jsonOut = new JsonOutput()
    def json = jsonOut.toJson(commandMap);
    json = jsonOut.prettyPrint(json)
    cacheFile.delete()
    cacheFile << json

    println "[+] Indexing finished, ${commandMap.size()} templates indexed"
    if (cliArgs.v)
        println "[+] JSON:\n${json}"
} else if (cliArgs?.l) {
    commandMap.sort().each { key, value ->
        if (!cliArgs.f || key.toLowerCase().contains(cliArgs.f))
            println "[+] ${key}" 
    }
} else if (cliArgs?.t) {
    if (commandMap.containsKey(cliArgs.t)){
        try {
            def templateContent = new File(commandMap[cliArgs.t].path).text
            templateContent = linkingTemplates(templateContent, cliArgs, commandMap) 
            
            if(cliArgs.p) {
                listPlaceHolders(templateContent, cliArgs)
            } else {
                def templateStr = replacePlaceHolders(templateContent, cliArgs)
                println "[+] ${templateStr}"
                if (cliArgs.c) {
                    def ss = new java.awt.datatransfer.StringSelection(templateStr)
                    java.awt.Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
                }
            }
        } catch(Exception e) {
            println "[!] error invokeing method for template: ${cliArgs.t} - ${e}"
            if (cliArgs.v)
                e.printStackTrace()
        }
    } else
        println "[!] Template not found: ${cliArgs.t}"
} else if (cliArgs.mdpb) {
    println "<div style=\"page-break-after: always;\"></div>"
} else if (cliArgs?.toc) {
    if (cliArgs?.v)
        println "[+] generating TOC ..."
    String toc = getTOCFromFile(cliArgs.toc.toString())
    println toc
} else if (cliArgs?.tables) {
    if (cliArgs?.v)
        println "[+] generating Table ..."
    String tableFromColumns = getTableForColumns(cliArgs.tables)
    println tableFromColumns
} else {
    cli.usage()
}

if (cliArgs.c && cliArgs?.t) {
    println "[+] You can now press CTRL+V to paste the template"
    println "[+] Keep the shell open until you have pasted your content"
    System.in.withReader { reader -> reader.readLine() }
}

if (cliArgs.v)
    println "[+] Done"

def String linkingTemplates(def templateContent, def cliArgs, def commandMap) {
    def matcher = (templateContent =~ /g-alot:link\{([a-zA-Z\-.0-9]+)\}/)
    def counter = 0
    if (cliArgs?.v)
        println "[+] collecting linked templates ..."
    while (matcher.find()) {
        def key = matcher.group(1)
        if (commandMap[key]?.path) {
            def newValue = new File(commandMap[key].path).text //commandMap[key].call()
            def toReplace = "g-alot:link\\{${key}\\}"
            templateContent = templateContent.replaceAll(toReplace, newValue)
            counter++

            if (cliArgs?.v)
                println "[+] linked template:\n\tKey : ${key}\n\tFile: ${commandMap[key].path}"
        } else {
            if (cliArgs?.v)
                println "[!] template not linked:\n\tKey : ${key}\n\tFile: ${commandMap[key]?.path}"
            throw new IllegalArgumentException("Could not link template: ${key} to path: ${commandMap[key]?.path}");
        }
    }
    if (cliArgs?.v)
        println "[+] ${counter} linked template(s)"
    
    return templateContent
}

def void listPlaceHolders(def templateContent, cliArgs) {
    def matcher = (templateContent =~ /g-alot\{([a-zA-Z\-.0-9]+)\}/)
    if (cliArgs?.v)
        println "[+] collecting placeholders in\n${templateContent}"
    Set placeHolders = new TreeSet()
    while (matcher.find()) {
        if (cliArgs?.v)
            println "[+] placeholder found: ${matcher.group(1)}"
        placeHolders.add(matcher.group(1))
    }
    if (cliArgs?.v)
        println "[+] ${placeHolders.size()} different placeolder(s) found"
    placeHolders.each { it ->
        println "[+] ${it}"
    }
    if (placeHolders.isEmpty()) {
        println "[+] No placeholders found, may use -v to get more informations"
    } else {
        println "galot -t ${cliArgs.t} -r " + placeHolders.join(":VALUE,") + ":VALUE"
    }
}

def String replacePlaceHolders(def templateContent, def cliArgs) {

    if (cliArgs.r) {
        cliArgs.rs.each { keyValue ->
            def keyValueArr = keyValue.split(":")
            def key = keyValueArr[0]
            def newValue = keyValueArr[1]
            def toReplace = "g-alot\\{${key}\\}"

            if (cliArgs?.v)
                println "[+] will replace: ${toReplace} => ${newValue}"
            if (newValue.startsWith("<")) {
                newValue = new File(newValue.replaceFirst("<", "")).text
                println "[+] Value to replace from file:\n${newValue}"
            }
            templateContent = templateContent.replaceAll(toReplace, newValue)
        }
    }
    return templateContent
}

static String getTableForColumns(columnNames) {

    String head = "|"
    String betweenHeadAndBody = "|"
    String body = "|"
    columnNames.each { colName -> 
        head += "${colName}|" 
        betweenHeadAndBody += (colName.replaceAll(/./, "-") + "|")
        body += (colName.replaceAll(/./, " ") + "|")
    }
    return "${head}\n${betweenHeadAndBody}\n${body}"
}

static String getTOCFromFile(String filePath) {
    
    new File(filePath).withReader { reader -> 
        return reader.lines()
            .filter{ line -> (line =~ /^##.+/).matches() }
            .map { line -> 
                String spaces = line.replaceAll(/^##(#*).+/, "\$1").replaceAll("#", " ")

                String name = line.replaceAll(/^#+\s*(.+)/, "\$1")

                String id = name.toLowerCase()
                                .replaceAll(/[^\da-zA-Z]+/, "-")
                                .replaceAll(/-$/, "") 

                return "${spaces}- [${name}](#${id})"
            }
            .collect()
            .join("\n")
    }
}
