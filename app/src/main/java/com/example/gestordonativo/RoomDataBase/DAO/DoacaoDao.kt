package gestortarefas.bambi.eduardo.RoomDataBase.DAO
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import gestortarefas.bambi.eduardo.RoomDataBase.Enitdades.DoacaoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DoacaoDAO  {

    @Insert
    suspend fun Adicionar(doacaoEntity: DoacaoEntity)

    @Update
    suspend fun Actualizar(doacaoEntity: DoacaoEntity)

    @Delete
    suspend fun Eliminar(doacaoEntity: DoacaoEntity)


    @Query("SELECT * FROM DoacaoEntity WHERE idusuario = :idusuario")
    fun ObterListaTudoPorIDUsuario(idusuario: Int): Flow<List<DoacaoEntity>>


    @Query("SELECT * FROM DoacaoEntity WHERE id = :id LIMIT 1")
    fun ObterDoacaoporID(id: Int): Flow<DoacaoEntity?>

}

