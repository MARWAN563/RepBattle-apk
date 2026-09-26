package com.example.ui.components

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.OpenInFull
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.ui.theme.AccentViolet
import com.example.ui.theme.AccentVioletContainer
import com.example.ui.theme.AccentVioletLight
import com.example.ui.theme.ArenaBackground
import com.example.ui.theme.ArenaBorder
import com.example.ui.theme.ArenaSurface1
import com.example.ui.theme.ArenaSurface2
import com.example.ui.theme.ArenaSurfaceContainer
import com.example.ui.theme.ArenaSurfaceHigh
import com.example.ui.theme.SecondaryGreen
import com.example.ui.theme.TextLight
import com.example.ui.theme.TextMuted
import com.example.util.HtmlImageParser
import com.example.util.ParsedHtmlImage

@Composable
fun DynamicHtmlImageRenderer(
    htmlContent: String,
    modifier: Modifier = Modifier,
    title: String = "Dynamically Linked HTML Media",
    compact: Boolean = false
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    val parsedImages = remember(htmlContent) {
        HtmlImageParser.extractImagesFromHtml(htmlContent)
    }

    var selectedImageForInspection by remember { mutableStateOf<ParsedHtmlImage?>(null) }
    var showSourceSnippet by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(ArenaSurface1)
            .border(1.dp, ArenaBorder, RoundedCornerShape(16.dp))
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Section Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(AccentViolet.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Link,
                        contentDescription = null,
                        tint = AccentVioletLight,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Column {
                    Text(
                        text = title,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextLight
                    )
                    Text(
                        text = "${parsedImages.size} dynamic asset(s) resolved from HTML",
                        fontSize = 11.sp,
                        color = TextMuted
                    )
                }
            }

            IconButton(
                onClick = { showSourceSnippet = !showSourceSnippet },
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Code,
                    contentDescription = "View Source",
                    tint = if (showSourceSnippet) AccentVioletLight else TextMuted,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        // Expandable HTML Snippet Inspector
        AnimatedVisibility(visible = showSourceSnippet) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(ArenaSurface2)
                    .border(0.5.dp, ArenaBorder, RoundedCornerShape(8.dp))
                    .padding(10.dp)
            ) {
                Text(
                    text = htmlContent.take(280) + if (htmlContent.length > 280) "..." else "",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 10.sp,
                    color = TextMuted,
                    lineHeight = 14.sp
                )
            }
        }

        // Rendered Dynamic Images
        if (parsedImages.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(ArenaSurfaceContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No <img> tags found in this HTML source",
                    fontSize = 12.sp,
                    color = TextMuted
                )
            }
        } else if (compact || parsedImages.size > 2) {
            // Horizontal Carousel for multiple assets
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(parsedImages) { item ->
                    DynamicImageThumbnail(
                        image = item,
                        onClick = { selectedImageForInspection = item }
                    )
                }
            }
        } else {
            // Stacked Cards
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                parsedImages.forEach { item ->
                    DynamicImageCard(
                        image = item,
                        onClick = { selectedImageForInspection = item }
                    )
                }
            }
        }
    }

    // Lightbox / Inspection Dialog
    selectedImageForInspection?.let { img ->
        Dialog(onDismissRequest = { selectedImageForInspection = null }) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .clip(RoundedCornerShape(18.dp))
                    .background(ArenaSurface1)
                    .border(1.dp, ArenaBorder, RoundedCornerShape(18.dp))
                    .padding(16.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Dynamic HTML Asset",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextLight
                        )
                        IconButton(
                            onClick = { selectedImageForInspection = null },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = TextLight
                            )
                        }
                    }

                    // Large Image Preview
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(ArenaBackground),
                        contentAlignment = Alignment.Center
                    ) {
                        SubcomposeAsyncImage(
                            model = img.url,
                            contentDescription = img.altText.ifEmpty { "Dynamic asset" },
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            val state = painter.state
                            if (state is AsyncImagePainter.State.Loading) {
                                CircularProgressIndicator(color = AccentViolet, modifier = Modifier.size(32.dp))
                            } else if (state is AsyncImagePainter.State.Error) {
                                Icon(imageVector = Icons.Default.BrokenImage, contentDescription = null, tint = TextMuted)
                            } else {
                                SubcomposeAsyncImageContent()
                            }
                        }
                    }

                    if (img.altText.isNotEmpty()) {
                        Text(
                            text = img.altText,
                            fontSize = 12.sp,
                            color = TextLight,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    // URL Copy Bar
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(ArenaSurface2)
                            .padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = img.url,
                            fontSize = 10.sp,
                            fontFamily = FontFamily.Monospace,
                            color = AccentVioletLight,
                            modifier = Modifier.weight(1f),
                            maxLines = 1
                        )
                        IconButton(
                            onClick = {
                                clipboardManager.setText(AnnotatedString(img.url))
                                Toast.makeText(context, "Image URL copied!", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = "Copy",
                                tint = TextLight,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DynamicImageThumbnail(
    image: ParsedHtmlImage,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(width = 110.dp, height = 120.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(ArenaSurfaceContainer)
            .border(1.dp, ArenaBorder, RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(6.dp)
            .testTag("dynamic_image_thumb")
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(ArenaBackground),
                contentAlignment = Alignment.Center
            ) {
                SubcomposeAsyncImage(
                    model = image.url,
                    contentDescription = image.altText,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                ) {
                    val state = painter.state
                    if (state is AsyncImagePainter.State.Loading) {
                        CircularProgressIndicator(color = AccentViolet, modifier = Modifier.size(18.dp), strokeWidth = 2.dp)
                    } else if (state is AsyncImagePainter.State.Error) {
                        Icon(imageVector = Icons.Default.BrokenImage, contentDescription = null, tint = TextMuted, modifier = Modifier.size(20.dp))
                    } else {
                        SubcomposeAsyncImageContent()
                    }
                }
            }

            Text(
                text = image.altText.ifEmpty { "HTML Asset" },
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextLight,
                maxLines = 1,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
private fun DynamicImageCard(
    image: ParsedHtmlImage,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(ArenaSurface2)
            .border(1.dp, ArenaBorder, RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(10.dp)
            .testTag("dynamic_image_card"),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(ArenaBackground),
            contentAlignment = Alignment.Center
        ) {
            SubcomposeAsyncImage(
                model = image.url,
                contentDescription = image.altText,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            ) {
                val state = painter.state
                if (state is AsyncImagePainter.State.Loading) {
                    CircularProgressIndicator(color = AccentViolet, modifier = Modifier.size(18.dp), strokeWidth = 2.dp)
                } else if (state is AsyncImagePainter.State.Error) {
                    Icon(imageVector = Icons.Default.BrokenImage, contentDescription = null, tint = TextMuted)
                } else {
                    SubcomposeAsyncImageContent()
                }
            }
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = image.altText.ifEmpty { "Referenced HTML Image" },
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = TextLight,
                maxLines = 1
            )
            Text(
                text = image.url,
                fontSize = 10.sp,
                fontFamily = FontFamily.Monospace,
                color = TextMuted,
                maxLines = 1
            )
        }

        Icon(
            imageVector = Icons.Default.OpenInFull,
            contentDescription = "Expand",
            tint = TextMuted,
            modifier = Modifier.size(18.dp)
        )
    }
}
