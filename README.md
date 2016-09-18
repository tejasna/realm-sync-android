# realm-sync-android

Realm-sync simplifies data retrieval from any REST API and caches it to local realm.io database on android devices.Built on top of loaders which was introduced in Android 3.0 and volley for transmitting Network Data.

Realm sync peovides the following 

1. Seamless network calls - provides interfaces to make network calls with very little code.
2. Serialization - serailizes json data into respective realm models and returns a list of realm objects.
3. Orientation changes - preserves state on orientation changes on an android device.
4. Offline caching.
5. All done under the hood with very little code and configuration.

# Use

  To start off create a realm-sync object as follows
  
  ```
  new RealmSync(MainActivity.this,
                            USER_OBJECT, 
                            END_POINT,   
                            REQUEST METHOD,
                            JSON_OBJECT); 
  ```
                            
  Use a loader to add your callbaks to your activity or fragment
  
  ```
  public LoaderManager.LoaderCallbacks<List<RealmObject>> loaderCallbacks =
            new LoaderManager.LoaderCallbacks<List<RealmObject>>() {

                public Loader<List<RealmObject>> onCreateLoader(int id, Bundle args) {
                    return new RealmSync(
                            MainActivity.this,
                            realmUser,
                            EndpointsEnum.Endpoints.OPEN_WEATHER,
                            Request.Method.GET, 
                            null);
                }

                @Override
                public void onLoadFinished(Loader<List<RealmObject>> loader,
                                           List<RealmObject> data) {
                    if (data.size() == 0) {
                        listViewAdapter.swapData(Collections.<RealmObject>emptyList());
                        Toast.makeText(MainActivity.this, getResources().getString(R.string.refresh_error), Toast.LENGTH_LONG).show();
                    } else {
                        listViewAdapter.swapData(data);
                    }
                    swipeContainer.setRefreshing(false);
                }

                @Override
                @SuppressWarnings("all")
                public void onLoaderReset(Loader<List<RealmObject>> loader) {
                    loader = null;
                }
            };
            
  ```

