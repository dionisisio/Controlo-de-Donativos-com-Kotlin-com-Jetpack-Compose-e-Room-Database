package gestortarefas.bambi.eduardo.Frontend.Paginas

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.gestordonativo.R
import gestortarefas.bambi.eduardo.Frontend.Componetes.ExibirMensagemSimples
import gestortarefas.bambi.eduardo.Frontend.ViewModel.DOacaoViewModel
import gestortarefas.bambi.eduardo.Frontend.ViewModel.UsuarioViewModel
import gestortarefas.bambi.eduardo.RoomDataBase.Enitdades.DoacaoEntity
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarDoacaoPage(navController: NavHostController, doacaoviewModel: DOacaoViewModel) {
    val doacao = doacaoviewModel.doacaoAtual.value

    var bemdoado by remember { mutableStateOf(doacao?.bemdoado ?: "") }
    var observacaodoacao by remember { mutableStateOf(doacao?.observacaodoacao ?: "") }
    var quantidadedoada by remember { mutableStateOf(doacao?.quantidadedoada ?: 0) }
    var doador by remember { mutableStateOf(doacao?.doador ?: "") }
    var beneficiario by remember { mutableStateOf(doacao?.beneficiario ?: "") }
    var local by remember { mutableStateOf(doacao?.local ?: "") }
    var datadoacao by remember { mutableStateOf(doacao?.datadoacao ?: "") }
    var horadoacao by remember { mutableStateOf(doacao?.horadoacao ?: "") }


    // Estados para validar campos
    var bemdoadoValido by remember { mutableStateOf(true) }
    var doadorvalidado by remember { mutableStateOf(true) }
    var beneficiariovalidade by remember { mutableStateOf(true) }


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
                        text = "Editar Registo de Donativo",
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
                isError = !doadorvalidado,
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
                isError = !beneficiariovalidade,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 8.dp)
            )

            OutlinedTextField(
                value = quantidadedoada.toString(),
                onValueChange = { input ->
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
                placeholder = { Text("Observação") },
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Data docção: $datadoacao", color = Color.Black, style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal))

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


                    if (bemdoado.isEmpty() || observacaodoacao.isEmpty() || doador.isEmpty() || beneficiario.isEmpty()) {
                        mensagemErro = "Os campos bem, doador e beneficiário devem ser preenchidos!"
                        mostrarMensagemSimples = true
                    } else {

                        val dataAtual = dateFormatter.format(Date())
                        val horaAtual = timeFormatter.format(Date())

                        if ((datadoacao.isNotEmpty() && datadoacao > dataAtual) || (datadoacao.isNotEmpty() && horadoacao.isNotEmpty() && (datadoacao == dataAtual && horadoacao > horaAtual))) {
                            mensagemErro = "Data e hora não podem ser superior ao momento atual."
                            mostrarMensagemSimples = true
                        } else {


                            val anotacaoAtualizada = doacao?.copy(
                                bemdoado = bemdoado,
                                observacaodoacao = observacaodoacao,
                                quantidadedoada = quantidadedoada,
                                doador = doador,
                                beneficiario = beneficiario,
                                datadoacao = datadoacao,
                                horadoacao = horadoacao
                            )

                            anotacaoAtualizada?.let {
                                doacaoviewModel.Atualizar(it)
                            }
                            navController.navigate("homepage")
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
                        text = "Actualizar",
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