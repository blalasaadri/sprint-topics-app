# sprint-topics-app

This is a small project for the first Senacor "24h Sprint". In this project I want to take a look at the [Jack and Jill](http://tools.android.com/tech-docs/jackandjill) toolchain for android.

## Findings
1. [android-apt](https://bitbucket.org/hvisser/android-apt) is no longer needed with Jack and Jill, as they come with their own annotation processor. In the gradle file, replace `apt`  with `annotationProcessor`.
2. [dagger](http://google.github.io/dagger/) version 2.2 causes problems with [butterknife](http://jakewharton.github.io/butterknife/). Upgrading to a newer version of dagger (2.4 or newer) solves this. ([Source](https://github.com/JakeWharton/butterknife/issues/617))
3. When using Jenkins (or any other CI tool) to build an android app, you have to agree to the license. In a non-UI world, this can be done with a trick documented [on StackOverflow](http://stackoverflow.com/questions/38096225/automatically-accept-all-sdk-licences); basically you copy the data from your `$ANDROID_SDK/licenses` directory to the CI environments sdk directory.
