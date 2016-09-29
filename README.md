# sprint-topics-app

This is a small project for the first Senacor "24h Sprint". In this project I want to take a look at the [Jack and Jill](http://tools.android.com/tech-docs/jackandjill) toolchain for android.

## Findings
1. [android-apt](https://bitbucket.org/hvisser/android-apt) is no longer needed with Jack and Jill, as they come with their own annotation processor. In the gradle file, replace `apt`  with `annotationProcessor`.
2. [dagger](http://google.github.io/dagger/) version 2.2 causes problems with [butterknife](http://jakewharton.github.io/butterknife/). Upgrading to a newer version of dagger (2.4 or newer) solves this. ([Source](https://github.com/JakeWharton/butterknife/issues/617))
