# JUpdate-AdoptOpenJDKUpdater [![CodeFactor](https://www.codefactor.io/repository/github/jgcomptech/jupdate-adoptopenjdkupdater/badge/master)](https://www.codefactor.io/repository/github/jgcomptech/jupdate-adoptopenjdkupdater/overview/master)
A command-line updater for Java for the AdoptOpenJDK.

This updater will check the AdoptOpenJDK repository releases for the newest versions and return info based on the specified parameters.

Options:
```
  -?, --help                          display this help message
  -a, --asset=<asset>                 sets the asset name to install
  -apiid, --apiid=<apiID>             sets the id for api usage
                                      (is ignored if both id and secret are not specified)
  -apisecret, --apisecret=<apiSecret> sets the secret for api usage
                                      (is ignored if both id and secret are not specified)
  -b, --boolean                       enables minimal output
  -d, --download                      downloads the installer
  -debug, --debug                     enables debug logging
  -dp, --downloadpath=<downloadPath>  sets the path to download to
  -h, --hotspot                       enables usage of hotspot jvm type (default)
  -i, --install                       downloads and installs the installer
  -info, --showassetinfo              shows info about the os appropriate asset
  -j9, --openj9                       enables usage of openj9 jvm type
  -jdk, --jdk                         enables usage of jdk (default)
  -jre, --jre                         enables usage of jre
  -jv, --javaVersion[=<version>]      the version of java to lookup (default: 11),
                                      if specified without parameter: 11
  -pre, --prerelease                  enables use of prerelease assets
  -r, --refresh                       overwrites the exclusions file
  -trace, --trace                     enables trace logging
  -version, --version                 display version info
```
      
## Basic Usage
If no arguments are supplied then the program will use the current LTS release, which is Java 11 and use JDK Hotspot version.

You can change this by supplying the folowing parameters:

**-jv [version], --javaVersion [version]**
You can supply any Java version, Java 8 or later.

**-jre**
If you want to retrieve info for the JRE instead of the JDK then use this.

**-j9 or -openj9**
If you want to retrieve info for OpenJ9 instead of the Hotspot then use this.

**-a, -asset [Asset Name]**
Allows you to specify an exact asset name for lookup. If this argument is not supplied then the asset will be the one that matches the current OS and archetechure.

**-pre, -prerelease**
Tells the program to lookup the newest prerelease instead of the newest stable release.

**-info, --showassetinfo**
Returns info about the found asset including a visual printout of the download link and version info

## Download and Install
Once the api finds a result it will return a download URL. This can be used to downloaad and install the file.
You can use a few arguments to do this in the application:

**-d, --download**
Downloads the file.

**-i, --install**
Downloads the file and runs the installer.

**-dp, --downloadpath**
Sets the folder where the file should be downloaaded to.

## Debugging

This app uses logging throughout and you can control how much logging will show in the console with the following arguments:

**-debug, --debug**
Sets the logging level to DEBUG

**-trace, --trace**
Sets the logging level to TRACE which is more verbose then just DEBUG


## API Setup
The GitHub API is how all update info is requested and to prevent server overload comes with a rate
limit. If unauthenticated the limit is only 60 requests an hour but if you use OAuth then the amount of requests
increases to 5000 an hour. More info about the rate limiting can be found [here](https://developer.github.com/v3/#rate-limiting).
To create a OAuth application, which is needed to recieve the client ID and secret, you can follow the official
guide for [here](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/). The URLs that
are requested in the form, the "Homepage URL" and the "Authorization callback URL" can be any URL as
they are completely ignored by the updater.

If you want to supply a set of login info for the github api then you can do it in 2 different ways:

### app.properties file
Create a app.properties file in either the same directory as the program or in the current working directory.

The file must have the following info:

```
client_id= [Github Client ID]
client_secret= [Github Client Secret]
use_oauth=true
```

If you want to quickly disable the file without just deleting it you can change "use_oauth" to false.

### Arguments -apiid and -apisecret

You can simply pass the -apiid and -apisecret arguments and it will use the credentials.

Exclusions
This program alows for user editible exclusions file for the search process.
If you edit the exclusions file then passing the -refresh argument will delete the file and create a new one with default settings.

## Download

This updater will be avalable with each release as a JAR file and an EXE file.
Currently both have a requirement of having at least a minimum of Java 8 installed.
We will be working on making the EXE file not have Java be required in the future.

You can access the releases [here](https://github.com/JGCompTech/JUpdate-AdoptOpenJDKUpdater/releases).
