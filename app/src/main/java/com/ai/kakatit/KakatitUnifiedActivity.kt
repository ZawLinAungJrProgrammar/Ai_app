package com.ai.kakatit

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.*

class KakatitUnifiedActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    KakatitMainScreen()
                }
            }
        }
    }
}

@Entity(tableName = "knowledge_table")
data class KnowledgeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val category: String,
    val content: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Dao
interface KnowledgeDao {
    @Query("SELECT * FROM knowledge_table")
    suspend fun getAllKnowledge(): List<KnowledgeEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKnowledge(item: KnowledgeEntity)
}

@Database(entities = [KnowledgeEntity::class], version = 1, exportSchema = false)
abstract class KakatitDatabase : RoomDatabase() {
    abstract fun knowledgeDao(): KnowledgeDao
    companion object {
        @Volatile private var INSTANCE: KakatitDatabase? = null
        fun getDatabase(context: Context): KakatitDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, KakatitDatabase::class.java, "kakatit_db").build()
                INSTANCE = instance
                instance
            }
        }
    }
}

@Composable
fun KakatitMainScreen() {
    var responseText by remember { mutableStateOf("မင်္ဂလာပါကိုကို၊ ကကတစ် အသင့်ရှိနေပါပြီရှင်။ 🪷💖") }
    Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.Center) {
        Card(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.padding(24.dp), contentAlignment = Alignment.Center) {
                Text(text = responseText, fontSize = 16.sp)
            }
        }
    }
}