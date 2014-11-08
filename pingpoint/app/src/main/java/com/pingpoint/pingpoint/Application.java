package com.pingpoint.pingpoint;

import com.parse.Parse;
import com.parse.ParseObject;

public class Application extends android.app.Application {

  public Application() {
  }

  @Override
  public void onCreate() {
    super.onCreate();
    ParseObject.registerSubclass(PingGroup.class);
    Parse.initialize(this, "SGiQCn0KyqRVzidsnDTuG7Hg1GKK4ZE6pSKj50hE",
        "xfsY6e3IcrYbFkEsldwZYTzJlRAg2gJJTvSJ9cqi");
  }

}
