# JAVA Starter Template

[![Release][release-image]][release-url]

[![License][license-image]][license-url]
[![Issues][issues-image]][issues-url]
[![Code maintainability][code-maintainability-image]][code-maintainability-url] [![Code issues][code-issues-image]][code-issues-url] [![Code Technical Debt][code-tech-debt-image]][code-tech-debt-url]

[![Codacy Badge][codacy-image]][codacy-url][![Codacy Badge][codacy-coverage-image]][codacy-url]

[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=gregoranders_com.github.gregoranders.template&metric=bugs)][sonarcloud-url]
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=gregoranders_com.github.gregoranders.template&metric=code_smells)][sonarcloud-url]
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=gregoranders_com.github.gregoranders.template&metric=coverage)][sonarcloud-url]
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=gregoranders_com.github.gregoranders.template&metric=duplicated_lines_density)][sonarcloud-url]
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=gregoranders_com.github.gregoranders.template&metric=ncloc)][sonarcloud-url]
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=gregoranders_com.github.gregoranders.template&metric=sqale_rating)][sonarcloud-url]
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=gregoranders_com.github.gregoranders.template&metric=alert_status)][sonarcloud-url]
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=gregoranders_com.github.gregoranders.template&metric=reliability_rating)][sonarcloud-url]
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=gregoranders_com.github.gregoranders.template&metric=security_rating)][sonarcloud-url]
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=gregoranders_com.github.gregoranders.template&metric=sqale_index)][sonarcloud-url]
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=gregoranders_com.github.gregoranders.template&metric=vulnerabilities)][sonarcloud-url]

[![Main Language][language-image]][code-metric-url] [![Languages][languages-image]][code-metric-url] [![Code Size][code-size-image]][code-metric-url] [![Repo-Size][repo-size-image]][code-metric-url]

## Features

- [Gradle][base-gradle-url]
- GitHub CI Integration (feature, development, master, release)
- Code Quality via [Code Climate][base-codeclimate-url], [Codacy][base-codacy-url] and [SonarCloud][base-sonarcloud-url]

## Required repository secrets
- [**CC_TEST_REPORTER_ID**](https://codeclimate.com)
- [**CODACY_PROJECT_TOKEN**](https://app.codacy.com)
- [**COVERALLS_REPO_TOKEN**](https://coveralls.io)
- [**SONAR_TOKEN**](https://sonarcloud.io/)

| GitHub                                                           | Coveralls                                                                  |
|------------------------------------------------------------------|----------------------------------------------------------------------------|
| [![ReleaseMaster Build][release-build-image]][release-url]       |                                                                            |
| [![Master Build][master-build-image]][master-url]                | [![Master Coverage][master-coveralls-image]][master-coveralls-url]         |
| [![Development Build][development-build-image]][development-url] | [![Test Coverage][development-coveralls-image]][development-coveralls-url] |


[release-url]: https://github.com/gregoranders/com.github.gregoranders.template/releases
[master-url]: https://github.com/gregoranders/com.github.gregoranders.template/tree/master
[development-url]: https://github.com/gregoranders/com.github.gregoranders.template/tree/development
[code-metric-url]: https://github.com/gregoranders/com.github.gregoranders.template/search?l=JAVA
[license-url]: https://github.com/gregoranders/com.github.gregoranders.template/blob/master/LICENSE
[license-image]: https://img.shields.io/github/license/gregoranders/com.github.gregoranders.template.svg
[issues-url]: https://github.com/gregoranders/com.github.gregoranders.template/issues
[issues-image]: https://img.shields.io/github/issues-raw/gregoranders/com.github.gregoranders.template.svg
[release-image]: https://img.shields.io/github/release/gregoranders/com.github.gregoranders.template
[release-build-image]: https://github.com/gregoranders/com.github.gregoranders.template/workflows/Release%20CI/badge.svg
[master-build-image]: https://github.com/gregoranders/com.github.gregoranders.template/workflows/Master%20CI/badge.svg
[development-build-image]: https://github.com/gregoranders/com.github.gregoranders.template/workflows/Development%20CI/badge.svg
[master-coveralls-url]: https://coveralls.io/github/gregoranders/com.github.gregoranders.template?branch=master
[master-coveralls-image]: https://img.shields.io/coveralls/github/gregoranders/com.github.gregoranders.template/master
[development-coveralls-image]: https://img.shields.io/coveralls/github/gregoranders/com.github.gregoranders.template/development
[development-coveralls-url]: https://coveralls.io/github/gregoranders/com.github.gregoranders.template?branch=development
[code-maintainability-url]: https://codeclimate.com/github/gregoranders/com.github.gregoranders.template/maintainability
[code-maintainability-image]: https://img.shields.io/codeclimate/maintainability/gregoranders/com.github.gregoranders.template
[code-issues-url]: https://codeclimate.com/github/gregoranders/com.github.gregoranders.template/maintainability
[code-issues-image]: https://img.shields.io/codeclimate/issues/gregoranders/com.github.gregoranders.template
[code-tech-debt-url]: https://codeclimate.com/github/gregoranders/com.github.gregoranders.template/maintainability
[code-tech-debt-image]: https://img.shields.io/codeclimate/tech-debt/gregoranders/com.github.gregoranders.template
[language-image]: https://img.shields.io/github/languages/top/gregoranders/com.github.gregoranders.template
[languages-image]: https://img.shields.io/github/languages/count/gregoranders/com.github.gregoranders.template
[code-size-image]: https://img.shields.io/github/languages/code-size/gregoranders/com.github.gregoranders.template
[repo-size-image]: https://img.shields.io/github/repo-size/gregoranders/com.github.gregoranders.template
[codacy-image]: https://app.codacy.com/project/badge/Grade/d1cd7b5422ef48958feb9765a36f79c1
[codacy-coverage-image]: https://app.codacy.com/project/badge/Coverage/d1cd7b5422ef48958feb9765a36f79c1
[codacy-url]: https://app.codacy.com/gh/gregoranders/com.github.gregoranders.template/dashboard
[sonarcloud-url]: https://sonarcloud.io/summary/new_code?id=gregoranders_com.github.gregoranders.template
[base-codeclimate-url]: https://codeclimate.com
[base-codacy-url]: https://app.codacy.com
[base-coveralls-url]: https://coveralls.io
[base-sonarcloud-url]: https://sonarcloud.io
[base-gradle-url]: https://gradle.org/
