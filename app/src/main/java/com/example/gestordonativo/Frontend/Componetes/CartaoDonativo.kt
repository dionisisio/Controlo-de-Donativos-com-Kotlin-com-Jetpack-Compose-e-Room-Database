package gestortarefas.bambi.eduardo.Frontend.Componetes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import gestortarefas.bambi.eduardo.RoomDataBase.Enitdades.DoacaoEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun CartaoDonativos(doacaoentity: DoacaoEntity, modifier: Modifier = Modifier) {
     Card(
        modifier = modifier
            .padding(5.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            if (doacaoentity != null) {
                Text(text = doacaoentity.id.toString() +"- "+ doacaoentity.bemdoado, style = MaterialTheme.typography.titleLarge)

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = doacaoentity.observacaodoacao, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Quantidade: "+ doacaoentity.quantidadedoada, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Doador: "+ doacaoentity.doador, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Beneficiário: "+ doacaoentity.beneficiario, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Doado aos: "+ doacaoentity.horadoacao+" de "+doacaoentity.datadoacao, style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(text = "Registado aos:  " + doacaoentity.dataregisto, style = MaterialTheme.typography.bodySmall)
            }

            }
        }
    }
}

fun getCurrentDate(): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return dateFormat.format(Date())
}
