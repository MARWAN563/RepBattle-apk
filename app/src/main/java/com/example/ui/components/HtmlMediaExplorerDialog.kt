package com.example.ui.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material.icons.filled.Html
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
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

data class HtmlSamplePreset(
    val title: String,
    val description: String,
    val html: String
)

object HtmlSamplePresets {
    val PRESETS = listOf(
        HtmlSamplePreset(
            title = "RepBattle Master",
            description = "Brand logo & Marouane athlete avatar",
            html = """
                <header class="brand-header">
                  <img src="https://lh3.googleusercontent.com/aida/AEtjO1Wlfvf--6qpgqqvjfpUuuto2OFCfYpxiA0HuZrOqppqY-rvEmCyxhDNETsW5NViYn3Fgzbi_J4DZt7uEZSOclb4fnq899RciUJgqtEQIpdelpHOVJJa07YrbU9WKPtqv492w6CunJPnIsNeqTp9wACogKdzeduIzdEAVniIU53piS6MTFB7PfGK3zYEXNc3IlfoV9x3gA0VO9oaPa16-3jTTptHCj4uyYn0O8UUgpVfQ2t08H2GUOegTzk" alt="RepBattle Emblem Logo" />
                  <img src="https://lh3.googleusercontent.com/aida/AEtjO1U-YmtFIeRVDkN44IvyQj1ESa8PAvf1iMtsO9DK0VSd4i41m5Z1ugL-h3neL8ykdjP2vktpuWgTL_sqVZGspsGC1FlTKUlag06OKgAYlDybPkoJuGfirCx1cb4xDBOqAZi5j8Ry3UCXqUuxzPLteCHu4UBP6nIiSFOrWUmKZdOWACzQus_fGEnHagnD3Cuepbt4PYZEkpKKrBDnEscnWCERlKjl_pLMJesyvoE69HV1DcGXnNZKY3dmI0vl" alt="Athlete Marouane" />
                </header>
            """.trimIndent()
        ),
        HtmlSamplePreset(
            title = "Rivals Showdown",
            description = "Adam Miller & Sarah Chen duel cards",
            html = """
                <div class="duel-roster">
                  <img src="https://lh3.googleusercontent.com/aida-public/AB6AXuCphduOty87-FkzyGfnaqZBwMebXfl924Eca2ehclLk3WSNer8s-xHrzhF3zToQ1EY4h1m54FCb3i7FkWZxQf7f32EOit-TSWB2qa7ZJAzKwnWyJ09kbvUZ8hCJP0m8Th_7HczYvg7QXVMWUUX3SDtFqZynKr2B8UJoNqA_FdJnIi2IQ6KiW0Y1iuJzSyLPUPeKNnY_LQc3yyt0mEcLtpZ5Ki_3n6HlUzSa4YJatXIz--7ifaEjlM9EXQ" alt="Adam Miller Roster Headshot" />
                  <img src="https://lh3.googleusercontent.com/aida-public/AB6AXuApuIm4PcujRGsDX42gn8pWkpinKEkLk3kYdsb2XfI2wSF23hFsPm2xdcZRbxIY3QpaSleZn1euVuaJXQWx2r8Hzy2AdQZTr0pSEDv8qjaApBpvnLkkStH9MWKFZciq8BDFjhOIq328HfORnzqBLlIhNYy5HZDPFM2Lx-ihWWubMY9-ZMHuuyYG4HdOrlzl3ZuKhClSVEqyf0V7-a2pQJjoilmhU7JxWKmEiuEsBr91HOmzMDJwzd_s4Q" alt="Sarah Chen Roster Headshot" />
                </div>
            """.trimIndent()
        ),
        HtmlSamplePreset(
            title = "Live Viewport CSS",
            description = "Atmospheric gym floor background",
            html = """
                <div class="camera-stage" style="background-image: url('https://lh3.googleusercontent.com/aida-public/AB6AXuBb-DIYxBt7VQxMXKYxXml5vDe28rY6kaRi0Wlz5_UEoBEMpe2MUsEMUyw93vo9_OZ55O0MXuQ_zSlt49ZBTq2hN4zw8DxxmmuESeGc8j8E8CXSSFHuKK2etlQROPSgcvSpjuaZ_BsrdquDnjtDPn-ZJpNxRqiOsB7gzumemNtJvRdMrAZwKAu_YyFIUG8HADYrg-JisBdvMZ7aLzyiHjqKGzh_h_12Ga6ndf_Y4BcsqneZK-EObsAROw');">
                  <span class="hud">Active Tracking</span>
                </div>
            """.trimIndent()
        )
    )
}

@Composable
fun HtmlMediaExplorerDialog(
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var selectedPresetIndex by remember { mutableStateOf(0) }
    var currentHtmlInput by remember { mutableStateOf(HtmlSamplePresets.PRESETS[0].html) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.88f)
                .clip(RoundedCornerShape(20.dp))
                .background(ArenaBackground)
                .border(1.dp, ArenaBorder, RoundedCornerShape(20.dp))
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Header
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
                                .size(36.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(AccentViolet.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Html,
                                contentDescription = null,
                                tint = AccentVioletLight,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "HTML Image Resolver",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black,
                                color = TextLight
                            )
                            Text(
                                text = "Parse and dynamically render images from HTML",
                                fontSize = 11.sp,
                                color = TextMuted
                            )
                        }
                    }

                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = TextLight
                        )
                    }
                }

                // Sample Preset Selector Chips
                Text(
                    text = "SELECT HTML TEMPLATE PRESET",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextMuted,
                    letterSpacing = 0.5.sp
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(HtmlSamplePresets.PRESETS.indices.toList()) { index ->
                        val preset = HtmlSamplePresets.PRESETS[index]
                        val isSelected = selectedPresetIndex == index

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(999.dp))
                                .background(if (isSelected) AccentViolet else ArenaSurfaceContainer)
                                .border(1.dp, if (isSelected) AccentViolet else ArenaBorder, RoundedCornerShape(999.dp))
                                .clickable {
                                    selectedPresetIndex = index
                                    currentHtmlInput = preset.html
                                }
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = preset.title,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) TextLight else TextMuted
                            )
                        }
                    }
                }

                // HTML Editor / Input Box
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "SOURCE HTML",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextMuted,
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            text = "Supports <img> & background: url()",
                            fontSize = 10.sp,
                            color = AccentVioletLight
                        )
                    }

                    OutlinedTextField(
                        value = currentHtmlInput,
                        onValueChange = { currentHtmlInput = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp)
                            .testTag("input_html_source"),
                        textStyle = androidx.compose.ui.text.TextStyle(
                            fontFamily = FontFamily.Monospace,
                            fontSize = 11.sp,
                            color = TextLight,
                            lineHeight = 15.sp
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = ArenaSurface1,
                            unfocusedContainerColor = ArenaSurface1,
                            focusedBorderColor = AccentViolet,
                            unfocusedBorderColor = ArenaBorder
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                }

                // Rendered Dynamic Images Output
                DynamicHtmlImageRenderer(
                    htmlContent = currentHtmlInput,
                    title = "Parsed Dynamic Images Output"
                )

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}
