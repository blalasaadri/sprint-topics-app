# Setup Jenkins to build an Android project

There are various hurdles to overcome when trying to build an android project with Jenkins. I'll try to list everything I did to get it working.

1. Install the following Plugins:
   * Android Emulator Plugin
   * Android Lint Plugin
   * Google Play Android Publisher Plugin
   * Timestamper (optional)
2. Setup the `ANDROID_HOME` environment variable:
   1. Jenkins > Manage Jenkins > Configure System
   2. check "Environment variables"
   3. add `ANDROID_HOME` with the value (in my case: `/var/jenkins_home/tools/android-sdk`)
   4. save
3. Create a new job with the following settings (for a GitHub project):
   * \[x] GitHub-Project > Project url = `<url to git project>`
   * Source-Code-Management:
     + Git, set up repository and credentials
     + Branches to build:
       - `*/master`
       - `*/feature/*`
     + Repository Browser:
       - githubweb
       - URL = `<github project url>`
   * Build triggers:
     + \[x] Build GitHub pull requests (setup your crontab)
     + \[x] Build when a change is pushed to GitHub
     + \[x] GitHub Pull Request Builder
     + \[x] Probe Code Management System (setup your crontab)
   * Build environment:
     + \[x] Clear build environment before build
     + \[x] Add timestamp to console output (optional)
   * Build settings
     + Add "Invoke Gradle script"
       - Use gradlew executable \[x]
       - From Root Build Script Dir \[x]
       - Build step description: `build`
       - Switches: `build`
