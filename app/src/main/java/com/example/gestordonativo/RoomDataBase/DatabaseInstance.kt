package gestortarefas.bambi.eduardo.RoomDataBase

import android.content.Context
import androidx.room.Room

object DatabaseInstance {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "bdcaridade"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}
