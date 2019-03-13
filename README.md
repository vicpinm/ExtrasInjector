 <p align="center">
  <img width="70%" src ="/logo.png" />
</p>

**Library written completely in Kotlin. It reduces boilerplate related with passing extras through activities and arguments through fragments. This library allows you to inject extras directly in the class you want, like your presenter if you use MVP, or your viewmodel if you use MVVM. See sample project for details.**

## Example with MVP

### Presenter class

Use @InjectExtra in any class where you want to get your extras injected. If your extra is optional, you have to specify it with the argument optional = true in the annotation.

```kotlin
@ForActivity(ActivityPresenter::class)
class MyPresenter {

    @InjectExtra lateinit var userId: Long  //Mandatory extra
    @InjectExtra(optional = true) var userDetails : UserDetails? = null //Optional extra
}
````

These extras will be obtained from an activity called 'ActivityPresenter', so you have to specify that with the anotation @ForActivity(class)/@ForFragment(class)

### View class

Inject your extras to the target class with ExtrasInjector.bind(activity/fragment instance, targetClass)
If you have a ParentActivity which holds a reference to your presenter, you can inject your extras there.

```kotlin
class ParentActivity : Activity {

    var presenter : Presenter
    
    initPresenter() {
        ExtrasInjector.bind(this, presenter)
    }
}
````

### Activity/fragment invocation

You can get a new intent for your activity with:

```kotlin
val myIntent = Activities.intentForDetailsActivity(context, extras...)
````

And for fragments:

```kotlin
val myFragment = Fragments.createDetailsFragment(extras...)
````
These generated methods allow you to pass only the arguments you have specified in your destination class (presenter, viewModel). Following this example, we have a class 'MyPresenter' with two annotated fields: userId: Long (mandatory) and userDetails: UserDetails? (optional). Therefore, these methods will expect a mandatory Long argument (userId), and an optional 'UserDetails' argument. You can check the sample project to see a real sample. 

## Download 

Grab via Gradle:

```groovy
repositories {
    mavenCentral()
}
//For androidX libraries compatibility:
implementation "com.github.vicpinm:extrasinjector:1.3.1"
kapt "com.github.vicpinm:extrasinjector-processor:1.3.1"

//If you use old appCompat libraries:
implementation "com.github.vicpinm:extrasinjector:1.2.0"
kapt "com.github.vicpinm:extrasinjector-processor:1.2.0"

```

