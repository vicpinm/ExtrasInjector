# ExtrasInjector 

## Description

Kotlin library for extras injection. 
Forget about that boilerplate associated with extras and arguments when creating new activities or fragments. 
This library allows you to inject extras directly in the class you want, like your presenter if you are using MVP.

## Example with MVP

### Presenter class

Use @InjectExtra in any class where you want to get your extras injected. If your extra is optional, you have to specify it with the argument optional = true in the annotation.

````
@ForActivity(ActivityPresenter::class)
class MyPresenter {

    @InjectExtra lateinit var userId: Long  //Mandatory extra
    @InjectExtra(optional = true) var userDetails : UserDetails? = null //Optional extra
}
````

### View class

Inject your extras to the target class with ExtrasInjector.bind(activity/fragment instance, targetClass)
If you have a ParentActivity which holds a reference to your presenter, you can inject your extras there.

````
class ParentActivity : Activity {

    var presenter : Presenter
    
    initPresenter() {
        ExtrasInjector.bind(this, presenter)
    }
}
````

### Activity/fragment invocation

You can get a new intent for your activity with:

````
Activities.intentFor<name of your activity>(context, extras...)
````

And for fragments:


````
Fragments.create<name of your fragment>(extras...)
````


## Download 

Grab via Gradle:

```groovy
repositories {
    mavenCentral()
}

implementation "com.github.vicpinm:extrasinjector:1.0.10"
kapt "com.github.vicpinm:extrasinjector-processor:1.0.8"

```

