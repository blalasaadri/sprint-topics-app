# Looking at "Jack and Jill"

This is a small project for the first Senacor *24h Sprint*. In this project I have taken a look at the [Jack and Jill](http://tools.android.com/tech-docs/jackandjill) toolchain for android.

## Findings
1. [android-apt](https://bitbucket.org/hvisser/android-apt) is no longer needed with Jack and Jill, as they come with their own annotation processor.
   In the gradle file, replace `apt`  with `annotationProcessor`.
2. [dagger](http://google.github.io/dagger/) version 2.2 causes problems with [butterknife](http://jakewharton.github.io/butterknife/).
   Upgrading to a newer version of dagger (2.4 or newer) solves this.
   ([Source](https://github.com/JakeWharton/butterknife/issues/617))
3. When using Jenkins (or any other CI tool) to build an android app, you have to agree to the license.
   In a non-UI world, this can be done with a trick documented [on StackOverflow](http://stackoverflow.com/questions/38096225/automatically-accept-all-sdk-licences); basically you copy the data from your `$ANDROID_SDK/licenses` directory to the CI environments sdk directory.
4. As of today (29th September 2016), Jack and Jill does work for third party libraries which use Java 8 functionalities (e.g. [Javaslang](http://www.javaslang.io/)).
   The issue is being worked on and its progress can be followed in the [android issue tracker](https://code.google.com/p/android/issues/detail?id=211386).
   There might be a workaround by turning the third party library into an `.aar` using the Android Maven Plugin (as stated [here](https://code.google.com/p/android/issues/detail?id=211386#c30)), but I haven't tested that as it seems overly complex.
5. Jack offers a long list of additional parameters, which in the `build.gradle` file can be set like such:

        android {
        ...
            defaultConfig {
            ...
                jackOptions {
                    enabled true
                    additionalParameters("jack.incremental" : true)
                }
            }
        ...
        }
    However, many of these are poorly documented.
    A full list of the parameters with very rudimentary documentation can be retrieved by the following command:

        $ java -jar <SDK>/build-tools/<build-tools-version>/jack.jar --help-properties

    The output of this command using the build tools version 24.0.3 can be found in the file `jack-parameters.txt`.

## What I didn't look at
* [Shrinking and Obfuscation support](http://tools.android.com/tech-docs/jackandjill#TOC-Shrinking-and-Obfuscation-support)
* [Repackaging support / jarjar](http://tools.android.com/tech-docs/jackandjill#TOC-Repackaging-support)
* [Jack server](https://android.googlesource.com/platform/prebuilts/sdk/+/master/tools/README-jack-server.md)
* the [$HOME/.jack](https://source.android.com/source/jack.html#$home_jack_file) file
* including `.aar` libraries
* switching [incremental build](https://source.android.com/source/jack.html#incremental_compilation) on and off (though official sources contradict each other on whether it's [enabled](http://tools.android.com/tech-docs/jackandjill#TOC-Compilation-support) or [disabled](https://source.android.com/source/jack.html#incremental_compilation) by default)
* [Multidex support](https://developer.android.com/studio/build/multidex.html) (for apps with over 64K methods - not directly related to Jack, but may become important when using some libraries)



## Build errors and their solutions
Here are a few build errors I faced and the solutions I came up with:
### `:app:lintFailed converting ECJ parse tree to Lombok for file <file>`
This problem is due to the fact that Android Lint doesn't *like* lambdas even though Android (using Jack) officially supports them since [at least API level 23](https://developer.android.com/guide/platform/j8-jack.html#supported-features).
A solution which I found [here](https://code.google.com/p/android/issues/detail?id=200887) is this:

In the `buildscript` portion of your gradle build file, add the following dependency:

    dependencies {
        ...
        // Use a version of lombok which understands lambdas
        classpath 'me.tatarka.retrolambda.projectlombok:lombok.ast:0.2.3.a2'
        ...
    }

### `:app:transformJackWithJackForDebugERROR: Dex writing phase: file '<jar file>', entry '<dex file>' cannot be read: incorrect data check`
This seems to be an error that occurs when a zip file is built incorrectly.
Simply rebuilding should normally do the trick.

### `:app:mergeDebugResourcesException in thread "<thread name>" java.lang.RuntimeException: Timed out while waiting for slave aapt process, make sure the aapt execute at <android sdk path>/build-tools/24.0.3/aapt can run successfully (some anti-virus may block it) or try setting environment variable SLAVE_AAPT_TIMEOUT to a value bigger than 5 seconds`

There seems to be a problem in some versions of the android build tools, which causes `aapt` to fail.
This problem has existed on and off for a while and the issue can be followed [here](https://code.google.com/p/android/issues/detail?id=188627).
A solution which worked for me was switching from the build tool version 24.0.3 back to 24.0.2.