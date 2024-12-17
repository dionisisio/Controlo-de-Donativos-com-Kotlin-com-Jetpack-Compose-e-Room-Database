package gestortarefas.bambi.eduardo.Frontend.ViewModel


import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.eduardo.RoomDataBase.Repositorios.DoacaoRepositorio
import gestortarefas.bambi.eduardo.RoomDataBase.Enitdades.DoacaoEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.Locale

class DOacaoViewModel(val doacaorepositorio: DoacaoRepositorio) : ViewModel() {

    private val _doacaoAtual = mutableStateOf<DoacaoEntity?>(null)
    val doacaoAtual: State<DoacaoEntity?> = _doacaoAtual

       fun selecionar(doacao: DoacaoEntity) {
           _doacaoAtual.value = doacao
    }

    fun Adicionar(doacaoEntity: DoacaoEntity) {
        viewModelScope.launch {
            doacaorepositorio.Adicionar(doacaoEntity)
        }
    }

    fun Atualizar(doacaoEntity: DoacaoEntity) {
        viewModelScope.launch {
            doacaorepositorio.Actualizar(doacaoEntity)
        }
    }

    fun Editar(doacaoEntity: DoacaoEntity) {
        selecionar(doacaoEntity)
        Atualizar(doacaoEntity)
    }


    fun Eliminar(doacaoEntity: DoacaoEntity) {
        viewModelScope.launch {
            doacaorepositorio.Eliminar(doacaoEntity)
        }
    }

    fun ObterDoacaorID(id: Int): Flow<DoacaoEntity?> {
        return doacaorepositorio.ObterDoacaoporID(id)
    }

    fun ObterListaPorUsuario(idusuario: Int): Flow<List<DoacaoEntity>> {
        return doacaorepositorio.ObterListaPorUsuario(idusuario)
    }

}
