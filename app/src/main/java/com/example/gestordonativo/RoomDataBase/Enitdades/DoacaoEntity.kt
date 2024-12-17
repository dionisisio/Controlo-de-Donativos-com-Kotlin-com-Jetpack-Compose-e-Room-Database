package gestortarefas.bambi.eduardo.RoomDataBase.Enitdades

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DoacaoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val idusuario: Int,
    val bemdoado: String,
    val observacaodoacao: String,
    val quantidadedoada: Int,
    val doador: String,
    val beneficiario: String,
    val local: String,
    val dataregisto: String,
    val datadoacao: String,
    val horadoacao: String
)