# SoleekTask



# Note :

packages of this project are feature-based -> "every screen is in package"

* **addplan** : containg classes of page2 involving adding and interacting with user.
* **weddingdetails** : contains classes,adapters ... of page1
* **data** : represent the Model layer in MVP. contains three important classes.

1. **WeddingRepository**.
class to hide methods invocation and encapsulate LocalDataSource and RemoteDataSource.
2. **LocalDataSource**
class responsible for caching data (storing plans in our case)
3. **RemoteDataSource**.
to interact with the server.


-------------------

## Tools:

* retorfit2 for android client
* Rxjava2 and RxAndroid2 for asyncronous and event based functionalities.
* okhttp as a client for retrofit.
* Picasso for loading and caching images.
* SqlBrite : a lite-weight library works as a wraper for SQL.
* ButterKnife to inject views during compile time.
* other UI third-party librarieslike Juda,Matessi,Timber.


-----------------------------
## video of the project 
https://www.youtube.com/watch?v=BlZzZ_XS9Xc&feature=youtu.be.
