/*
 * MIT License
 *
 * Copyright (c) 2021 Gregor Anders
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.gregoranders.template.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction
import org.gradle.internal.os.OperatingSystem

class ProjectConfigurationPlugin implements Plugin<Project> {

    static class CPDTask extends DefaultTask {

        @TaskAction
        def cpd() {
            File outDir = project.file("${project.buildDir}/reports/cpd")
            outDir.mkdirs()
            ant.taskdef(name: 'cpd', classname: 'net.sourceforge.pmd.cpd.CPDTask',
                    classpath: project.configurations.pmd.asPath)
            ant.cpd(minimumTokenCount: '40', format: 'xml',
                    outputFile: new File(outDir, 'main.xml')) {
                fileset(dir: "src/main/java") {
                    include(name: '**/*.java')
                }
            }
        }
    }

    @Override
    void apply(Project project) {
        processJava(project)
        processIDEA(project)
        processJaCoCo(project)
        processCheckStyle(project)
        processPMD(project)
        processSpotBugs(project)
        processDependencyCheck(project)
        processJlink(project)
    }

    def processJava(Project projectInternal) {

        if (projectInternal.plugins.hasPlugin('java')) {

            projectInternal.jar {

                archiveBaseName = "${projectInternal.group}.${projectInternal.name}"
                archiveVersion = "${projectInternal.version}"

                def manifestAttributes = [
                        'Created-By'              : "${projectInternal.properties['author']}",
                        'Specification-Title'     : "${projectInternal.properties['description']}",
                        'Specification-Version'   : "${projectInternal.version}",
                        'Specification-Vendor'    : "${projectInternal.properties['author']}",
                        'Specification-Vendor-Id' : "${projectInternal.properties['author']} <${projectInternal.properties['email']}>",
                        'Implementation-Title'    : "${projectInternal.properties['description']} - Implementation",
                        'Implementation-Version'  : "${projectInternal.version}",
                        'Implementation-Vendor'   : "${projectInternal.properties['author']}",
                        'Implementation-Vendor-Id': "${projectInternal.properties['author']} <${projectInternal.properties['email']}>"
                ]

                if (projectInternal.properties['mainClass']) {
                    manifestAttributes.put('Main-Class', "${projectInternal.properties['mainClass']}")
                }

                manifest {
                    attributes(manifestAttributes)
                }
            }
        }
    }

    def processIDEA(Project projectInternal) {

        if (projectInternal.plugins.hasPlugin('idea')) {

            projectInternal.idea {

                module {
                    downloadJavadoc = true
                    downloadSources = true
                }

                project {
                    vcs = 'Git'
                    jdkName = "${projectInternal.sourceCompatibility}"
                    languageLevel = "${projectInternal.sourceCompatibility}"
                }
            }
        }
    }

    def processJaCoCo(Project projectInternal) {
        if (projectInternal.plugins.hasPlugin('jacoco')) {
            projectInternal.jacoco {
                toolVersion = projectInternal.properties['jacocoVersion']
                reportsDirectory = projectInternal.layout.buildDirectory.dir('reports/jacoco').get().asFile
            }
            projectInternal.jacocoTestReport {
                dependsOn projectInternal.test
                reports {
                    xml.required = true
                    xml.destination projectInternal.layout.buildDirectory.file("reports/jacoco/${name}.xml").get().asFile
                    csv.required = false
                    html.destination projectInternal.layout.buildDirectory.dir('reports/jacoco/html').get().asFile
                }
            }
            projectInternal.jacocoTestCoverageVerification {
                violationRules {
                    rule {
                        limit {
                            counter = 'INSTRUCTION'
                            minimum = 1.0
                        }
                        limit {
                            counter = 'BRANCH'
                            minimum = 1.0
                        }
                        limit {
                            counter = 'LINE'
                            minimum = 1.0
                        }
                        limit {
                            counter = 'METHOD'
                            minimum = 1.0
                        }
                        limit {
                            counter = 'CLASS'
                            minimum = 1.0
                        }
                    }
                }
            }
            projectInternal.test {
                jacoco {
                    destinationFile = projectInternal.layout.buildDirectory.file("results/jacoco/jacoco-${name}.exec").get().asFile
                    classDumpDir = projectInternal.layout.buildDirectory.dir('results/classpathdumps').get().asFile
                    excludes = ["**/Immutable*"]
                }
                finalizedBy projectInternal.jacocoTestReport
            }
            projectInternal.check {
                dependsOn projectInternal.jacocoTestCoverageVerification
            }
        }
    }

    def processCheckStyle(Project projectInternal) {
        if (projectInternal.plugins.hasPlugin('checkstyle')) {
            projectInternal.checkstyle {
                toolVersion = projectInternal.properties['checkstyleVersion']
                ignoreFailures = false
                configProperties = [
                        'checkstyle.header.file'      : projectInternal.file("${projectInternal.rootProject.projectDir}/config/checkstyle/checkstyle-header.txt"),
                        'checkstyle.suppressions.file': projectInternal.file("${projectInternal.rootProject.projectDir}/config/checkstyle/checkstyle-suppressions.xml")
                ]
            }
        }
    }

    def processPMD(Project projectInternal) {
        if (projectInternal.plugins.hasPlugin('pmd')) {
            projectInternal.pmd {
                consoleOutput = true
                incrementalAnalysis = true
                toolVersion = projectInternal.properties['pmdVersion']
                ignoreFailures = false
                ruleSets = []
                ruleSetConfig = projectInternal.resources.text.fromFile(projectInternal.file("$projectInternal.rootProject.projectDir/config/pmd/pmd-rules.xml"))
            }

            def cpdTask = projectInternal.tasks.register('cpd', CPDTask)

            projectInternal.check.configure {
                dependsOn cpdTask
            }
        }
    }

    def processSpotBugs(Project projectInternal) {
        if (projectInternal.plugins.hasPlugin('com.github.spotbugs')) {
            projectInternal.spotbugs {
                toolVersion = projectInternal.properties['spotbugsVersion']
                ignoreFailures = false
                effort = 'max'
                reportLevel = 'low'
                excludeFilter = projectInternal.rootProject.file('config/spotbugs/excludeFilter.xml')
            }
            projectInternal.spotbugsMain {
                reports {
                    html {
                        required = true
                        outputLocation = projectInternal.layout.buildDirectory.file('reports/spotbugs/main/spotbugs.html').get().asFile
                        stylesheet = 'fancy-hist.xsl'
                    }
                    xml {
                        required = true
                        outputLocation = projectInternal.layout.buildDirectory.file('reports/spotbugs/main/spotbugs.xml').get().asFile
                    }
                }
            }
            projectInternal.spotbugsTest.enabled = false
        }
    }

    def processDependencyCheck(Project projectInternal) {
        if (projectInternal.plugins.hasPlugin("org.owasp.dependencycheck")) {
            projectInternal.dependencyCheck {
                autoUpdate = true
                failBuildOnCVSS = 1
                cveValidForHours = 1
                format = 'ALL'
                outputDirectory = projectInternal.layout.buildDirectory.dir('reports/dependency-check').get().asFile
                suppressionFile = projectInternal.file("${projectInternal.rootProject.projectDir}/config/dependency-check/dependency-check-suppressions.xml")
                analyzers {
                    pyDistributionEnabled = false
                    pyPackageEnabled = false
                    rubygemsEnabled = false
                    opensslEnabled = false
                    cmakeEnabled = false
                    autoconfEnabled = false
                    composerEnabled = false
                    nodeEnabled = false
                    nuspecEnabled = false
                    assemblyEnabled = false
                }
            }
            projectInternal.check.configure {
                dependsOn projectInternal.dependencyCheckAnalyze
            }
        }
    }

    def processJlink(Project projectInternal) {
        if (projectInternal.plugins.hasPlugin('org.beryx.jlink')) {
            projectInternal.jlink {
                imageDir = projectInternal.layout.buildDirectory.dir("${projectInternal.rootProject.group}.${projectInternal.rootProject.name}").get().asFile
                imageZip = projectInternal.layout.buildDirectory.file("distributions/${projectInternal.rootProject.group}.${projectInternal.rootProject.name}-${projectInternal.rootProject.version}.zip").get().asFile
                options = ['--strip-debug', '--compress', '2', '--no-header-files', '--no-man-pages']
                launcher {
                    name = projectInternal.properties['jlinkExecutable']
                    noConsole = OperatingSystem.current().windows
                }
                jpackage {
                    imageName = "${projectInternal.rootProject.group}.${projectInternal.rootProject.name}-${projectInternal.rootProject.version}"
                    if (OperatingSystem.current().windows) {
                        installerOptions += ['--win-per-user-install', '--win-dir-chooser', '--win-menu', '--win-shortcut']
                        imageOptions += ['--win-console']
                    }
                }
            }

            projectInternal.jlinkZip {
                group = 'distribution'
            }
        }
    }
}
