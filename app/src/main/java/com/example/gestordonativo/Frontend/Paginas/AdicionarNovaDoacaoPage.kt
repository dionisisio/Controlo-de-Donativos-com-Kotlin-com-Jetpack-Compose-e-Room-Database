package gestortarefas.bambi.eduardo.Frontend.Paginas


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import java.text.SimpleDateFormat
import java.util.*

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import com.example.gestordonativo.R
import gestortarefas.bambi.eduardo.Frontend.Componetes.ExibirMensagemSimples
import gestortarefas.bambi.eduardo.Frontend.ViewModel.DOacaoViewModel
import gestortarefas.bambi.eduardo.Frontend.ViewModel.UsuarioViewModel
import gestortarefas.bambi.eduardo.RoomDataBase.Enitdades.DoacaoEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdicionarDoacaoPage(navController: NavHostController, doacaoviewModel: DOacaoViewModel, usuarioViewModel: UsuarioViewModel) {


    val usuarioAtual = usuarioViewModel.usuarioAtual
    var idusuario by remember { mutableStateOf(0) }

    if (usuarioAtual != null) {
        idusuario= usuarioAtual.id
    }

    var bemdoado by remember { mutableStateOf("") }
    var observacaodoacao by remember { mutableStateOf("") }
    var quantidadedoada by remember { mutableStateOf(0) }
    var doador by remember { mutableStateOf("") }
    var beneficiario by remember { mutableStateOf("") }
    var local by remember { mutableStateOf("") }
    var datadoacao by remember { mutableStateOf("") }
    var horadoacao by remember { mutableStateOf("") }


    // Estados para validar campos
    var bemdoadoValido by remember { mutableStateOf(true) }
    var doadorValido by remember { mutableStateOf(true) }
    var beneficiariovalido by remember { mutableStateOf(true) }

    // Estado para controlar o DatePickerDialog
    var MostarDatePicker by remember { mutableStateOf(false) }
    var MostarTimePicker by remember { mutableStateOf(false) }

    // Estado para controlar a visibilidade do AlertDialog simples
    var mostrarMensagemSimples by remember { mutableStateOf(false) }
    var mensagemErro by remember { mutableStateOf("") }

    val calendario = Calendar.getInstance()

    val dateFormatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Adicionar Donativos",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.background
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.popBackStack() },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.background,
                shape = CircleShape
            ) {
                Image(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Adicionar",
                    modifier = Modifier.size(50.dp)
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top, // Itens alinhados ao topo
            horizontalAlignment = Alignment.Start
        ) {

            OutlinedTextField(
                value = bemdoado,
                onValueChange = { bemdoado = it },
                label = { Text("Bem Doado") },
                placeholder = { Text("Inserir o bem") },
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp
                ),
                isError = !bemdoadoValido,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 8.dp)
            )
            OutlinedTextField(
                value = doador,
                onValueChange = { doador = it },
                label = { Text("Doador") },
                placeholder = { Text("Doador") },
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp
                ),
                isError = !doadorValido,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 8.dp)
            )
            OutlinedTextField(
                value = beneficiario,
                onValueChange = { beneficiario = it },
                label = { Text("Beneficiário") },
                placeholder = { Text("Beneficiário") },
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp
                ),
                isError = !beneficiariovalido,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 8.dp)
            )

            OutlinedTextField(
                value = quantidadedoada.toString(), // Converte o Int para String
                onValueChange = { input ->
                    // Tenta converter o texto de entrada para Int e atualiza a variável
                    quantidadedoada = input.toIntOrNull() ?: 0
                },
                label = { Text("Quantidade") },
                placeholder = { Text("Quantidade") },
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number // Configura o teclado numérico
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 8.dp)
            )
            OutlinedTextField(
                value = local,
                onValueChange = { local = it },
                label = { Text("Local") },
                placeholder = { Text("Local") },
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 120.dp)
                    .padding(bottom = 16.dp),
                maxLines = 10
            )
            OutlinedTextField(
                value = observacaodoacao,
                onValueChange = { observacaodoacao = it },
                label = { Text("Observação") },
                placeholder = { Text("Inserir observação") },
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 120.dp)
                    .padding(bottom = 16.dp),
                maxLines = 10
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Data doação: $datadoacao", color = Color.Black, style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal))

                Button(
                    onClick = { MostarDatePicker = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    ),
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(text = "Definir")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Linha para Hora Prevista
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Hora doação: $horadoacao", color = Color.Black, style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal))

                Button(
                    onClick = { MostarTimePicker = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    ),
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(text = "Definir")
                }
            }


            FloatingActionButton(
                onClick = {
                    if (bemdoado.isEmpty()  || doador.isEmpty()  || beneficiario.isEmpty()) {
                        mensagemErro = "Os campos bem, doador e benefiário devem ser preenchidos!"
                        mostrarMensagemSimples = true
                    } else {

                        val dataAtual = dateFormatter.format(Date())
                        val horaAtual = timeFormatter.format(Date())

                        if ((datadoacao.isNotEmpty() && datadoacao > dataAtual) || (datadoacao.isNotEmpty() && horadoacao.isNotEmpty() && (datadoacao == dataAtual && horadoacao > horaAtual))) {
                            mensagemErro = "Data e hora não podem ser superior ao momento atual."
                            mostrarMensagemSimples = true
                        } else {

                            val dataAtual =
                                SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
                            val novaAnotacao = DoacaoEntity(
                                id = 0,
                                idusuario = idusuario,
                                bemdoado = bemdoado,
                                observacaodoacao = observacaodoacao,
                                quantidadedoada=quantidadedoada,
                                doador = doador,
                                beneficiario = beneficiario,
                                local= local,
                                dataregisto = dataAtual,
                                datadoacao = datadoacao,
                                horadoacao = horadoacao
                            )
                            doacaoviewModel.Adicionar(novaAnotacao)
                            navController.popBackStack() // Voltar para a tela anterior
                        }
                    }

                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp)
                    .width(200.dp)
                    .height(56.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.primary,
                                    MaterialTheme.colorScheme.secondary
                                )
                            ),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Adicionar",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    )
                }
            }


            if (MostarDatePicker) {
                DatePickerDialog(
                    LocalContext.current,
                    { _, year, month, dayOfMonth ->
                        datadoacao = dateFormatter.format(Calendar.getInstance().apply {
                            set(year, month, dayOfMonth)
                        }.time)
                    },
                    calendario.get(Calendar.YEAR),
                    calendario.get(Calendar.MONTH),
                    calendario.get(Calendar.DAY_OF_MONTH)
                ).show()
                MostarDatePicker = false
            }


            if (MostarTimePicker) {
                TimePickerDialog(
                    LocalContext.current,
                    { _, hourOfDay, minute ->
                        horadoacao = timeFormatter.format(Calendar.getInstance().apply {
                            set(Calendar.HOUR_OF_DAY, hourOfDay)
                            set(Calendar.MINUTE, minute)
                        }.time)
                    },
                    calendario.get(Calendar.HOUR_OF_DAY),
                    calendario.get(Calendar.MINUTE),
                    true
                ).show()
                MostarTimePicker = false
            }
        }

    }

    ExibirMensagemSimples(
        MostrarMensagemSimples = mostrarMensagemSimples,
        mensagem = mensagemErro,
        onDismiss = { mostrarMensagemSimples = false }
    )
}
