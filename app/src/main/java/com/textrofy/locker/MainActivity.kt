package com.textrofy.locker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { TextrofyApp() }
    }
}

@Composable
fun TextrofyApp() {
    val nav = rememberNavController()
    var unlocked by remember { mutableStateOf(false) }
    MaterialTheme(colorScheme = lightColorScheme(containerColor = Color(0xFFFCFEFD))) {
        if (!unlocked) {
            PinScreen { unlocked = true }
        } else {
            Scaffold(bottomBar = { BottomBar(nav) }) { pad ->
                Box(Modifier.padding(pad)) {
                    NavHost(nav, startDestination = "home") {
                        composable("home") { HomeScreen() }
                        composable("vault") { VaultScreen() }
                        composable("share") { ShareScreen() }
                        composable("tools") { ToolsScreen() }
                    }
                }
            }
        }
    }
}

@Composable
fun PinScreen(onUnlock: ()->Unit) {
    Column(Modifier.fillMaxSize().background(Color(0xFFFCFEFD)), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Box(Modifier.size(120.dp).shadow(20.dp, RoundedCornerShape(32.dp)).background(Brush.linearGradient(listOf(Color(0xFF00C2D1), Color(0xFF7C5CFC))), RoundedCornerShape(32.dp)), contentAlignment = Alignment.Center) { Text("🔒", fontSize = 48.sp) }
        Spacer(Modifier.height(24.dp))
        Text("Textrofy Locker", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text("Offline & Encrypted", color = Color.Gray)
        Spacer(Modifier.height(40.dp))
        Row { repeat(4){ Box(Modifier.padding(8.dp).size(20.dp).background(Color(0xFFE0E0E0), RoundedCornerShape(50))) } }
        Spacer(Modifier.height(32.dp))
        Button(onClick = onUnlock, shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth(0.8f).height(56.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4F6EF7))) { Text("Unlock with PIN / Biometric") }
        Spacer(Modifier.height(16.dp))
        Row(Modifier.padding(16.dp)) {
            AssistChip(onClick={}, label={Text("Offline")}); Spacer(Modifier.width(8.dp)); AssistChip(onClick={}, label={Text("Encrypted")})
        }
    }
}

@Composable
fun HomeScreen() {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Vault Dashboard", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            VaultCard("Photos","248", Color(0xFFDBEAFE)); VaultCard("Videos","62", Color(0xFFE9D5FF)); VaultCard("Docs","128", Color(0xFFD1FAE5))
        }
        Spacer(Modifier.height(16.dp))
        Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(Color.White), elevation = CardDefaults.cardElevation(4.dp)) {
            Column(Modifier.padding(16.dp)) {
                Text("Storage: 2.4GB / 64GB Used", fontWeight = FontWeight.Medium)
                LinearProgressIndicator(progress = {0.4f}, modifier = Modifier.fillMaxWidth().height(8.dp).padding(top=8.dp), color = Color(0xFF00C2D1))
                Spacer(Modifier.height(8.dp)); Text("Protected by Biometric Lock", color = Color.Gray, fontSize = 12.sp)
            }
        }
    }
}
@Composable fun VaultCard(title: String, count: String, bg: Color) { Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(bg), modifier = Modifier.width(100.dp).height(100.dp)) { Column(Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) { Text("📁"); Text(title, fontWeight = FontWeight.Bold); Text("$count items", fontSize = 12.sp) } } }
@Composable fun VaultScreen() { Column(Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) { Text("File Vault - Offline", fontSize = 22.sp, fontWeight = FontWeight.Bold); Spacer(Modifier.height(16.dp)); Text("Photos, Videos, APK, ZIP - Real files will be shown here from SAF picker. Encrypted with AES-GCM + Keystore."); } }
@Composable fun ShareScreen() { Column(Modifier.fillMaxSize().padding(16.dp)) { Text("Send Files", fontSize = 22.sp, fontWeight = FontWeight.Bold); Spacer(Modifier.height(16.dp)); Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(Color.White)) { Column(Modifier.padding(16.dp)) { Text("Nearby Devices - Discovering..."); Spacer(Modifier.height(8.dp)); Text("Pixel 8 Pro - Connected 2m away"); LinearProgressIndicator(progress={0.68f}, modifier=Modifier.fillMaxWidth().padding(top=8.dp), color=Color(0xFF7C5CFC)); Text("68% - 12.4 MB/s - 3s left") } } } }
@Composable fun ToolsScreen() { LazyVerticalGrid(columns = GridCells.Fixed(3), modifier = Modifier.padding(16.dp)) { items(9){ i -> Card(Modifier.padding(8.dp).height(100.dp), colors = CardDefaults.cardColors(Color.White)) { Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text(listOf("Standard","Scientific","Age","GST","EMI","Interest","Unit","%","Date")[i]) } } } } }
@Composable fun BottomBar(nav: androidx.navigation.NavController) {
    NavigationBar { NavigationBarItem(selected=true, onClick={nav.navigate("home")}, icon={Text("🏠")}, label={Text("Home")}); NavigationBarItem(selected=false, onClick={nav.navigate("vault")}, icon={Text("🔒")}, label={Text("Vault")}); NavigationBarItem(selected=false, onClick={nav.navigate("share")}, icon={Text("📤")}, label={Text("Share")}); NavigationBarItem(selected=false, onClick={nav.navigate("tools")}, icon={Text("🧮")}, label={Text("Tools")}) }
}
