# Spring REST Docs (API 문서 자동화)
[공식문서 링크](https://docs.spring.io/spring-restdocs/docs/current/reference/html5/)

## 준비물
1. asciidoctor 플러그인
```
# build.gradle

plugins {
    id "org.asciidoctor.jvm.convert" version "3.3.2"
}
```

2. restdocs 라이브러리
```
# build.gradle

ext {
    restdocsVersion = '2.0.6.RELEASE'
    snippetsDir = file('build/generated-snippets')
}
configurations {
    asciidoctorExt
}
dependencies {
    asciidoctorExt "org.springframework.restdocs:spring-restdocs-asciidoctor:$restdocsVersion"
    testImplementation "org.springframework.restdocs:spring-restdocs-mockmvc:$restdocsVersion"
}
```

3. build script
```
tasks.named('test') {
    outputs.dir snippetsDir
    useJUnitPlatform()
}
asciidoctor {
    inputs.dir snippetsDir
    configurations 'asciidoctorExt'
    dependsOn test
}
task copyDocument(type: Copy) {
    dependsOn asciidoctor
    from file(asciidoctor.outputDir)
    into file("${sourceSets.main.output.resourcesDir}/static/docs")
}
bootJar {
    dependsOn copyDocument
}
```
