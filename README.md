# TheRedPlanetMars
View images of Mars.

Updated 3/1/17:
 - Add JUnit tests for presenters using Mockito
 
Updated 2/13/17:
 - Add a favorites database using realm
 - Add a favorites tab to the bottom navigation

Updated:
 - Add ability to share selected image via a share intent. 
 - Uses DataBinding for details View. 

This is a basic android app for practicing the MVP structure and jUnit/Espresso testing in Android. It is currently in progress.

 - Connects to NASA JSON API to retrieve images from the Mars rovers (Curiosity, Spirit, Opportunity).
 - Uses GSON, Retrofit, RxJava.
 - Able to select date of the pictures.
 - Uses material design and fluid animations. 
 - Uses Bottom Bar or navigation/Removed navigation drawer
 - Theme colors
 - Espresso/Junit tests. 
 
Need to implement:
 - Espresso test
 
 # Screenshots
 
 <img src="/screenshots/Screenshot_20170213-221702.png" alt="image" width="200">
 <img src="/screenshots/Screenshot_20170213-221706.png" alt="image" width="200">
 <img src="/screenshots/Screenshot_20170213-221726.png" alt="image" width="200">
 <img src="/screenshots/Screenshot_20170213-225641.png" alt="image" width="200">
 
<img src="/screenshots/Screenshot_20161215-002429.png" alt="image" width="200">
<img src="/screenshots/Screenshot_20161215-002449.png" alt="image" width="200">
<img src="/screenshots/Screenshot_20161215-002502.png" alt="image" width="200">
<img src="/screenshots/Screenshot_20161215-002518.png" alt="image" width="200">
<img src="/screenshots/Screenshot_20161215-002524.png" alt="image" width="200">

License
-------

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
