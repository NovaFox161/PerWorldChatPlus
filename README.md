#PerWorldChatPlus

##About
PerWorldChatPlus is a Bukkit plugin that makes chat per world, handles various other chat functions and servers as an API for developers.
<br>
PWCP handles chat in a unique way. Funnelling it through many validators, placeholders and filters, PWCP is able to make chat per world (or grouped), detect and block swears, spam, and ads, and more!


##Current Features
- Per world chat (can be disabled)
- Automatic update checker and automatic update downloader (if enabled via config.yml).
- Chat sharing across worlds. Link worlds together so they share their chat.
- Chat bypass to make your chat global.
- Timed Global chat. Make chat global for a limited period of time.
- Alert System (add a word to your list and get notified of when it is said in chat).
- Mention system (get a notice when you are mentioned in chat).
- Integrated ChatColor support. Make your messages colorful without needing to use codes every time!
- Along with the integrated ChatColor support, PerWorldChatPlus supports a GUI selector for ChatColors!
- Anti-Swear system. Block those bad words!
- Anti-Ad System. Block all the unwanted advertisements!
- And much much more!

##Planned Features (in order of importance)
- PerWorldChatPlus as standalone chat provider.
- More variables in format.
- Improvement to the developer API.
- Anti-spam (WIP).
- Better detection of IP Addresses.
- Better detection of websites.
- And more!!

##Issues
Should you find an issue with PerWorldChatPlus, please create a new issue in the Issues pages on this repository or via Dev Bukkit with a proper ticket.

##For Developers
PerWorldChatPlus also functions as a versatile API! <br>
To use this API within your own Bukkit plugin, please follow the directions below:
<br> **NOTE**: This only applies to Maven projects currently.
1. In your pom.xml insert the following repository and depend, where version is the target version (API only exists in 5.0.0 and higher):
```xml
<repository>
        <id>nova-public</id>
        <url>http://repo.novafox161.com/repository/nova-public/</url>
    </repository>
```
```xml
    <dependency>
        <groupId>com.cloudcraftgaming</groupId>
        <artifactId>PerWorldChatPlus-API</artifactId>
        <version>6.0.0-SNAPSHOT</version>
    </dependency>
```
2. Use the javadoc provided here: https://docs.novamaday.com/plugins/perworldchatplus/

3. And now you can start using PWCP in your plugin. Just check the Javadoc or wiki for extra help!

## Contributing
1. Fork this repo and make changes in your own copy
2. Add a test if applicable and run the existing tests with `mvn clean test` to make sure they pass
3. Commit your changes and push to your fork `git push origin master`
4. Create a new pull request and submit it back to us!
<br> <br>
**NOTE**: Please do not build. PWCP has resources that are not committed to GitHub and will fail if not present.
<br>x
**NOTE**: We will never claim ownership or responsibility for something we did not make. You will be given full credit for your contributions.