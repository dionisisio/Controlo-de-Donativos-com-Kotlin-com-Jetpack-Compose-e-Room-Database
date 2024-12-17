package gestortarefas.bambi.eduardo.Frontend.Paginas

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.gestordonativo.R
import gestortarefas.bambi.eduardo.Frontend.Componetes.CartaoDonativos
import gestortarefas.bambi.eduardo.Frontend.ViewModel.DOacaoViewModel
import gestortarefas.bambi.eduardo.Frontend.ViewModel.UsuarioViewModel
import java.util.*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(navController: NavHostController, viewModel: DOacaoViewModel, usuarioViewModel: UsuarioViewModel) {
    var inputdoacao by remember { mutableStateOf("") }
    val usuarioAtual = usuarioViewModel.usuarioAtual
    var idusuario by remember { mutableStateOf(0) }

    if (usuarioAtual != null) {
        idusuario= usuarioAtual.id

    }

    val doacaolista by viewModel.ObterListaPorUsuario(idusuario).collectAsState(initial = emptyList())




    val filteredList = doacaolista.filter { doacao ->
        doacao.bemdoado.contains(inputdoacao, ignoreCase = true) ||
       doacao.doador.contains(inputdoacao, ignoreCase = true) ||
       doacao.beneficiario.contains(inputdoacao, ignoreCase = true) ||
       doacao.observacaodoacao.contains(inputdoacao, ignoreCase = true) ||
       doacao.datadoacao.contains(inputdoacao, ignoreCase = true)
    }


    val sortedList = filteredList.sortedBy { doacao ->
        val date = doacao.datadoacao.split("/").takeIf { it.size == 3 }?.let {
            Calendar.getInstance().apply {
                set(it[2].toInt(), it[1].toInt() - 1, it[0].toInt()) // Convertendo para um objeto Date
            }
        }
        date?.time ?: Date()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Lista de Doações",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.background
                        )

                        if (usuarioAtual != null) {
                            Text(
                                text = "Usuário logado: ${usuarioAtual.nomeusuario}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.background
                            )
                        }
                    }
                },
                actions = {

                    IconButton(onClick = {
                        usuarioViewModel.FazerLogout()
                        navController.navigate("loginpage") { // Substitua pelo nome correto da sua tela de login
                            popUpTo("homePage") { inclusive = true } // Fecha a página de home ao fazer logout
                        }
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.logout), // Coloque o ícone de logout adequado
                            contentDescription = "Logout",
                            tint = MaterialTheme.colorScheme.background
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("adicionardoacaopage") },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.background,
                shape = CircleShape
            ) {
                Image(
                    painter = painterResource(id = R.drawable.adicionar),
                    contentDescription = "Adicionar",
                    modifier = Modifier.size(50.dp)
                )
            }
        }
    ) { paddingValues ->

        Column(modifier = Modifier.padding(paddingValues)) {
            TextField(
                value = inputdoacao,
                onValueChange = { inputdoacao = it },
                label = { Text("Pesquisar") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.onTertiary, // Cor de fundo
                    focusedLabelColor = Color.Gray, // Cor do label quando em foco (Cinza)
                    unfocusedLabelColor = Color.Gray, // Cor do label quando não em foco (Cinza)
                    focusedIndicatorColor = MaterialTheme.colorScheme.primaryContainer, // Cor da linha de indicação quando em foco
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f), // Cor da linha de indicação quando não em foco
                ),
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp
                )
            )

            if (sortedList.isEmpty()) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "Lista vazia",
                        color = Color.Black,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            } else {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = PaddingValues(10.dp)
                ) {
                    items(sortedList) { doacao ->

                        viewModel.selecionar(doacao)
                        CartaoDonativos(doacao,
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                                .clickable {

                                    navController.navigate("detalhesdoacaopage")
                                }
                        )
                    }
                }
            }
        }
    }
}
