package gestortarefas.bambi.eduardo.RoomDataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import gestortarefas.bambi.eduardo.Frontend.Componetes.Converters
import gestortarefas.bambi.eduardo.RoomDataBase.DAO.DoacaoDAO
import gestortarefas.bambi.eduardo.RoomDataBase.DAO.UsuarioDAO
import gestortarefas.bambi.eduardo.RoomDataBase.Enitdades.DoacaoEntity
import gestortarefas.bambi.eduardo.RoomDataBase.Enitdades.UsuarioEntity

@Database(entities = [UsuarioEntity::class, DoacaoEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDAO
    abstract fun doacaoDao(): DoacaoDAO
}
