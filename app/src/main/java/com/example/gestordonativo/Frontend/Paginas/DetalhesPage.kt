package gestortarefas.bambi.eduardo.Frontend.Paginas

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.gestordonativo.R
import gestortarefas.bambi.eduardo.Frontend.ViewModel.DOacaoViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalhesDoacaoPage(navController: NavHostController, doacaoViewModel: DOacaoViewModel) {

    var doacao = doacaoViewModel.doacaoAtual.value
    var MostrarMensagemdeConfirmacaodeEliminacao by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (doacao != null) {
                        Text(
                            text = "Bem doado: " + doacao.bemdoado,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.background
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary)
            )
        },
        floatingActionButton = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp) // Espaçamento para evitar sobreposição com a barra de navegação
            ) {
                Row(
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight()
                        .align(Alignment.BottomEnd), // Alinha a linha no canto inferior direito
                    horizontalArrangement = Arrangement.spacedBy(8.dp), // Espaçamento entre os botões
                    verticalAlignment = Alignment.CenterVertically // Alinha os botões verticalmente no centro da linha
                ) {
                    // Botão Voltar
                    FloatingActionButton(
                        onClick = { navController.popBackStack() },
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.background,
                        shape = CircleShape
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.back),
                            contentDescription = "Voltar",
                            modifier = Modifier.size(50.dp)
                        )
                    }

                    // Botão Excluir
                    FloatingActionButton(
                        onClick = {
                            // Exibe a confirmação de exclusão
                            MostrarMensagemdeConfirmacaodeEliminacao = true
                        },
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.background,
                        shape = CircleShape
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.delete),
                            contentDescription = "Excluir",
                            modifier = Modifier.size(50.dp)
                        )
                    }

                    // Botão Editar
                    FloatingActionButton(
                        onClick = {
                            if (doacao != null) {
                                doacaoViewModel.selecionar(doacao)
                                // Navega para a tela de Editar
                                navController.navigate("editardoacaopage")
                            }
                        }, // Seleciona a anotação para edição
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.background,
                        shape = CircleShape
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.edit),
                            contentDescription = "Editar",
                            modifier = Modifier.size(50.dp)
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                if (doacao != null) {
                    Text(
                        text = "Doador : " + doacao.doador,
                        fontSize = 16.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Justify,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                if (doacao != null) {
                    Text(
                        text = "Beneficiário : " + doacao.beneficiario,
                        fontSize = 16.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Justify,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                if (doacao != null) {
                    Text(
                        text = "Local : " + doacao.local,
                        fontSize = 16.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Justify,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                if (doacao != null) {
                    Text(
                        text = "Data de Entrega :  " + doacao.datadoacao + " "+doacao.horadoacao,
                        fontSize = 16.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Justify,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                if (doacao != null) {
                    Text(
                        text = "Obs: "+doacao.observacaodoacao,
                        color = Color.Black,
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Justify,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.height(50.dp))


                if (doacao != null) {
                    Text(
                        text = "Registado aos ${doacao.dataregisto}",
                        fontSize = 10.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        // Exibindo o AlertDialog de confirmação
        if (MostrarMensagemdeConfirmacaodeEliminacao) {
            AlertDialog(
                onDismissRequest = { MostrarMensagemdeConfirmacaodeEliminacao = false },
                title = { Text(text = "Confirmar Exclusão") },
                text = { Text(text = "Você tem certeza que deseja excluir este registo de doação?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            doacao?.let { doacaoViewModel.Eliminar(it) }
                            MostrarMensagemdeConfirmacaodeEliminacao = false
                            navController.popBackStack()
                        }
                    ) {
                        Text("Sim",
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 18.sp
                        )
                    }
                },
                dismissButton = {
                    TextButton(onClick = { MostrarMensagemdeConfirmacaodeEliminacao = false }) {
                        Text("Não",
                            color = Color.Black,
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 18.sp
                        )
                    }
                }
            )
        }
    }
}
