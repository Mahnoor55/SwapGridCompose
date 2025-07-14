# 🔄 SwapGridCompose for Jetpack Compose

A lightweight and customizable **drag-and-swap grid or row list** for Jetpack Compose. Supports row and grid layouts with smooth item swapping using drag gestures.

---

## ✨ Features

- 🔁 Drag-to-swap support in **rows** and **grids**
- 👆 Clickable and fully customizable item content
- 🧩 Built with Jetpack Compose
- 📦 Works with avatars, images, or any content

---

## 📦 Installation

### Step 1: Add JitPack to your `settings.gradle` (or `settings.gradle.kts`):

```gradle
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

### Step 2: Add the library dependency in your app's `build.gradle` (or `build.gradle.kts`):

```gradle
implementation("com.github.Mahnoor55:SwapGridCompose:v1.0.0")
```

---

## 🧪 Usage

### 🔹 Swap Grid Example:

```kotlin
 val images = remember {
            mutableStateListOf(
                R.drawable.j1, R.drawable.j2, R.drawable.j3, R.drawable.j4,
                R.drawable.j5, R.drawable.j6, R.drawable.j7, R.drawable.j8, R.drawable.j9,
            )
        }

 DragSwapGrid(
            listSize = images.size,
            columns = 3,
            boxSize = 80.dp,
            onSwap = { from, to ->
                images.apply {
                    val temp = this[from]
                    this[from] = this[to]
                    this[to] = temp
                }
            }
        ) { index ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(3.dp)
            ) {
                Image(
                    painter = painterResource(id = images[index]),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.FillBounds
                )
            }
        }
    }
```

### 🔹 Swap Row Example:

```kotlin
val imageList = remember {
    mutableStateListOf(R.drawable.f1, R.drawable.f2, R.drawable.p1, R.drawable.p2)
}

DragSwapRow(
    listSize = imageList.size,
    boxSize = 90.dp,
    onSwap = { fromIndex, toIndex ->
        val temp = imageList[fromIndex]
        imageList[fromIndex] = imageList[toIndex]
        imageList[toIndex] = temp
    }
) { index ->
    Image(
        painter = painterResource(id = imageList[index]),
        contentDescription = null,
        modifier = Modifier.size(90.dp).clip(RoundedCornerShape(8.dp)),
        contentScale = ContentScale.Crop
    )
}
```

---

## 📸 Demo

![Demo](https://github.com/Mahnoor55/SwapGridCompose/blob/main/demo.gif)
![Demo](https://github.com/Mahnoor55/SwapGridCompose/blob/main/demo2.gif)

---

## 📄 License

MIT License. Feel free to use, modify, and contribute!
