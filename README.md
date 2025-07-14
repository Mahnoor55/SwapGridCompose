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
val avatarList = remember {
    mutableStateListOf(
        R.drawable.manager,
        R.drawable.supervisor,
        R.drawable.tech_avatar,
        R.drawable.support_avatar
    )
}

DragSwapGrid(
    listSize = avatarList.size,
    columns = 2,
    boxSize = 100.dp,
    onSwap = { fromIndex, toIndex ->
        val temp = avatarList[fromIndex]
        avatarList[fromIndex] = avatarList[toIndex]
        avatarList[toIndex] = temp
    }
) { index ->
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = avatarList[index]),
            contentDescription = null,
            modifier = Modifier.size(80.dp).clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Text(
            text = "Role ${index + 1}",
            fontSize = 14.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
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

---

## 📄 License

MIT License. Feel free to use, modify, and contribute!
