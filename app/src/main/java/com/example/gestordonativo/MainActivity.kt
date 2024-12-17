package com.example.gestordonativo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.eduardo.RoomDataBase.Repositorios.DoacaoRepositorio
import com.example.eduardo.RoomDataBase.Repositorios.UsuarioRepositorio
import com.example.gestordonativo.ui.theme.DoacaoTheme
import gestortarefas.bambi.eduardo.Frontend.Paginas.AdicionarDoacaoPage
import gestortarefas.bambi.eduardo.Frontend.Paginas.CriarUsuarioPage
import gestortarefas.bambi.eduardo.Frontend.Paginas.DetalhesDoacaoPage
import gestortarefas.bambi.eduardo.Frontend.Paginas.EditarDoacaoPage
import gestortarefas.bambi.eduardo.Frontend.Paginas.HomePage
import gestortarefas.bambi.eduardo.Frontend.Paginas.ListaUsuariosPage
import gestortarefas.bambi.eduardo.Frontend.Paginas.LoginPage
import gestortarefas.bambi.eduardo.Frontend.Paginas.SplashPage
import gestortarefas.bambi.eduardo.Frontend.ViewModel.DOacaoViewModel
import gestortarefas.bambi.eduardo.Frontend.ViewModel.UsuarioViewModel
import gestortarefas.bambi.eduardo.RoomDataBase.DatabaseInstance
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Envolvendo o conteúdo no tema personalizado
            DoacaoTheme {
                var MostarTelaSplash by remember { mutableStateOf(true) }

                LaunchedEffect(Unit) {
                    delay(3000) // Aguarda 3 segundos
                    MostarTelaSplash = false
                }

                if (MostarTelaSplash) {
                    SplashPage() // Mostra a tela de splash
                } else {
                    // Criar a instância do banco de dados
                    var database = DatabaseInstance.getDatabase(applicationContext)

                    // Criar os repositórios
                    var usuariorepositorio = UsuarioRepositorio(database.usuarioDao())
                    var doacaorepositorio = DoacaoRepositorio(database.doacaoDao())

                    // Instanciar os ViewModel
                    val usuarioViewModel = UsuarioViewModel(usuariorepositorio)
                    val doacaoViewModel = DOacaoViewModel(doacaorepositorio)

                    // Iniciar a navegação
                    NavigationComponent(doacaoViewModel, usuarioViewModel)
                }
            }
        }
    }
}

@Composable
fun NavigationComponent(doacaoviewModel: DOacaoViewModel, usuarioViewModel: UsuarioViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "loginpage") {
        //Usuario
        composable("loginpage") { LoginPage(navController, usuarioViewModel) }
        composable("criarusuariopage") { CriarUsuarioPage(navController, usuarioViewModel) }
        composable("listausuariopage") { ListaUsuariosPage(navController, usuarioViewModel) }

        // Anotacoes
        composable("homepage") { HomePage(navController, doacaoviewModel, usuarioViewModel) }

        composable("adicionardoacaopage") { AdicionarDoacaoPage(navController, doacaoviewModel, usuarioViewModel) }

        composable("detalhesdoacaopage") { DetalhesDoacaoPage(navController,doacaoviewModel) }

        composable("editardoacaopage") { EditarDoacaoPage(navController,doacaoviewModel) }
    }
}

