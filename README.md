#Unofficial PlayStation Network Library for Java
Copyright 2012 Kroboth Software

License [Apache 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)

Sony hasn't published a _public_ API interface that retrieving psn info so developers have to make their own implementation. Some examples are
* http://www.psnapi.com.ar/
* http://www.psnapi.com/
* http://psnapi.codeplex.com/

While they are mostly server APIs, _psn-lib_ is a pure Java Library.

##Client
`PlayStationNetworkClient` is the client used to retrieve all network related data including profiles, games, and trophies.
````java
PlayStationNetworkClient psnClient = new PlayStationNetworkClient();
// need to call init in order to set up client
psnClient.init();
````
The client is broken up into three types of data
* Client -  All data is retrieved from [UK](http://uk.playstation.com/) site and some is stored on the client. Includes friends, games, and trophies for current client login.
* Public - Public games and trophies from [US](http://us.playstation.com/) site.
* Official - The term _Official_ refers to data Sony devices retrieve which is more compact. The `init` method needs to be called before hand to set up authorizations.


**Client Login**
````java
try {
  // progressListener is first argument, this case there is none. 
  psnClient.clientLogin(null, "username", "password");
} catch (PlayStationNetworkException e) {
  // login was unsuccessful, which can mean if service is down
} catch (PlayStationNetworkLoginException e) {
	// username and password are incorrect
} catch (IOException e) {
	// IO Exception
}
````
This will retrieve 

**Client Friends**
````java
try {
  psnClient.getClientFriendList();
} catch (PlayStationNetworkException e) {
	// problem parsing data
} catch (PlayStationNetworkLoginException e) {
	// login cookies invalid; needs login
} catch (IOException e) {
	// IO Exception
}
````

**Public Games**
````java
try {
psnClient.getPublicGameList("psnId");
} catch (PlayStationNetworkException e) {
	// problem parsing data
} catch (IOException e) {
	// IO Exception
}
````
`getPublicTrophyList()` does however need US Login to get _TICKET_ and _PSNS2STICKET_ cookies. `clientLogin` will get these cookies. Both cookies are static meaning they don't change value so it's possible to generate them and insert into `CookieManager`. Methods `PsnUtils.createLoginCookieTicket()` and `PsnUtils.createLoginCookiePsnTicket()` will create them based on any valid psnId. Note that psnId used for cookies doesn't have to same as one being used in _getPublicTrophyList_

**Official Games**
````java
try {
  // jid, start, max, Platforms
  psnClient.getOfficialGameList("jid", 0, 10, PlatformType.PS3, PlatformType.VITA);
} catch (IOException e) {
	// IO Exception
} catch (PlayStationNetworkException e) {
	// problem parsing data
}
````

##Values
**Jid** is abbreviated for [Jabble Id](http://en.wikipedia.org/wiki/JID#Decentralization_and_addressing). Every registered psn Id has a Jid.
````java
try {
  psnClient.getOfficialJid("psnId");
} catch (IOException e) {
	// IO Exception
}
````

**Trophy Link Id** refers to link Id for game on UK and US websites. 
`http://us.playstation.com/playstation/psn/profile/trophies/582938-Warhawk`
Id is **582938-Warhawk**.
`http://uk.playstation.com/psn/mypsn/trophies/detail/?title=4`
Id is **4**.
It can also be retrieved by `PsnGameData.getTrophyLinkId()`

##Network
The client uses `com.krobothsoftware.commons.network` package as a helper for doing network connections, handling cookies, and authorizations.

`NetworkHelper` class can be retrieved by `PlayStationNetworkClient.getNetworkHelper()`
````java
CookieManager cookieManager = networkHelper.getCookieManager();
cookieManager.putCookie(PsnUtils.createLoginCookieTicket("psnId"));
cookieManager.putCookie(PsnUtils.createLoginCookiePsnTicket("psnId"));
````
All cookies are made using builder class `commons.network.values.Cookie.Builder`

````java
networkHelper
  	.setDefaultHeader("User-Agent","Mozilla/5.0 (Windows; U; Windows NT 6.1; rv:2.2) Gecko/20110201");
networkHelper.setDefaultConnectTimeout(5000); // millisecond
networkHelper.setDefaultReadTimeout(5000); // millisecond
````
Set-Default methods set value only if request connection hasn't set it; _set if not set_

##How to set up
Go to [Downloads](https://github.com/KrobothSoftware/psn-lib/downloads) and get lastest release. It includes library, source, and Javadoc. This project depends on the following libraries
* [Html-Cleaner](http://htmlcleaner.sourceforge.net/) - For parsing Html Data(websites)
* [SLF4J](http://www.slf4j.org/) - Logging library