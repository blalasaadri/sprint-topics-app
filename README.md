# sprint-topics-app

This is a small project for the first Senacor "24h Sprint". In this project I want to take a look at the [Jack and Jill](http://tools.android.com/tech-docs/jackandjill) toolchain for android.

## Findings
1. [android-apt](https://bitbucket.org/hvisser/android-apt) is no longer needed with Jack and Jill, as they come with their own annotation processor. In the gradle file, replace `apt`  with `annotationProcessor`.
2. [dagger](http://google.github.io/dagger/) version 2.2 causes problems with [butterknife](http://jakewharton.github.io/butterknife/). Upgrading to a newer version of dagger (2.4 or newer) solves this. ([Source](https://github.com/JakeWharton/butterknife/issues/617))
3. When using Jenkins (or any other CI tool) to build an android app, you have to agree to the license. In a non-UI world, this can be done with a trick documented [on StackOverflow](http://stackoverflow.com/questions/38096225/automatically-accept-all-sdk-licences); basically you copy the data from your `$ANDROID_SDK/licenses` directory to the CI environments sdk directory.

## Build errors and their solutions
Here are a few build errors I faced and the solutions I came up with:
### `:app:lintFailed converting ECJ parse tree to Lombok for file <file>`
This problem is tue to the fact, that Android Lint doesn't "like" Lambdas.
A solution which I found [here](https://code.google.com/p/android/issues/detail?id=200887) is this:

In the `buildscript` portion of your gradle build file, add the following dependency:

    dependencies {
      // Use a version of lombok which understands lambdas
      classpath 'me.tatarka.retrolambda.projectlombok:lombok.ast:0.2.3.a2'
    }

### `:app:transformJackWithJackForDebugERROR: Dex writing phase: file '<jar file>', entry '<dex file>' cannot be read: incorrect data check`
This seems to be an error that occurs when a zip file is built incorrectly.
Simply rebuilding should normally do the trick.

