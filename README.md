# GlobalOverlayService #

I went through about 10 other implementations of the global floating overlays and none of them were as modular as I would have liked. So, this is my implementation.

Goals:

1. Minimal setup.
2. Looks "good enough".


## Features ##

1. Easy, readable implementation
2. Overlay view dragging
3. Simple onClick and onRemove listeners
4. Close view by dragging to an "X" view at the bottom of the screen
5. Simple demo



## Simple Usage ##

1. Set this project as a dependency. In your app module's `build.gradle`, add the following lines:

        repositories {
            maven {
                url "https://jitpack.io"
            }
        }

        dependencies {
            compile 'com.github.danialgoodwin:android-global-overlay:v0.2'
        }

2. Subclass `GlobalOverlayService` and call `addOverlayView(View)`. Example working code:

        public class MySimpleOverlayService extends GlobalOverlayService {

            @Override
            public void onCreate() {
                super.onCreate();

                ImageView view = new ImageView(this);
                view.setImageResource(R.mipmap.ic_launcher);
                addOverlayView(view, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toast("onClick");
                    }
                });
            }

            private void toast(String message) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }

        }

3. In `AndroidManifest.xml`, add your `Service` and add the required Android permission:

        <manifest ...>

            <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW"/>

            <application ...>
                ...
                <service android:name=".MySimpleOverlayService"
                    android:exported="false"
                    android:enabled="true" />
            </application>
        </manifest>

4. Call `startService()` whenever you want to show the global overlay. For example, in my `MainActivity.java`:

        private void startOverlay() {
            startService(new Intent(this, MySimpleOverlayService.class));
        }

Done. Most cases won't need to do any more than that. Though there are a few more methods to support other use cases.

`GlobalOverlayService` will handle everything else, including closing the overlay view when the `Service` is destroyed.

The library `GlobalOverlayService` has a few more features available:
- In the above sample, where the `OnClickListener` is passed in as an argument, there's an overloaded method that also allows an `OnLongClickListener` and `OnOverlayRemoveListener`. (Note: OnLongClickListener isn't implemented yet.)
- Change what the "remove view" looks like by overriding `onGetRemoveView()`.
- You can remove the overlay without destroying the `Service` by calling `removeOverlayView(View)` with the same view you used in `addOverlayView(View)`.
- The `OnRemoveOverlayListener.onRemoveOverlay` provides an argument that takes into account whether or not the user is the one to remove the overlay. (I'm using this info for analytics)



## Limitations ##

- Currently, the `OnLongClickListener` isn't implemented. (Mainly, because I didn't need it, but I provide a starting point in the source code. ;) )
- The minSdkVersion is 15. There's nothing fancy about the library, so I'm sure it will work for lower APIs, but I haven't checked.



## Possible Gotchas ##

- When the user manually removes the overlay (by dragging to the removeView), the service will be destroyed.
- Currently, only one floating overlay view is allowed at a time.



## Contributing ##

Have fun. Pull requests welcomed. This was a very simple library to create and is simple to modify and extend.

Want ideas?
- How about fully implementing the OnLongClickListener?
- Allowing library implementer to setup by just providing the resDrawableId, rather than the full View.
- Better default UX for overlay view
- Better default UX for remove view
- Allowing library implementer to stylize components rather than replacing them. Perhaps defaulting to match theme.

[AOSP code style](https://source.android.com/source/code-style.html) preferred.

### Modifying the drag style ###

The current implementation is set in `addOverlayView()`, which calls `newSimpleOnTouchListener()`. So, you can either tweak that or just provide a different way for getting the required `OnTouchListener`.


### API Design considerations ###

- For my simple case, I didn't need to bind the service, so `GlobalOverlayService` implements `Service.onBind()` by always returning null.
- I was thinking about making a `protected abstract View mGlobalOverlayService.onCreateView()` so that implementers didn't have to worry about when to call `addOverlayView()` nor the `Service` lifecycle. But, as a trade-off it would be limiting for those who may not want an overlay for the entire life of the `Service`.





## License ##

    The MIT License (MIT)

    Copyright (c) 2015 Danial Goodwin

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
