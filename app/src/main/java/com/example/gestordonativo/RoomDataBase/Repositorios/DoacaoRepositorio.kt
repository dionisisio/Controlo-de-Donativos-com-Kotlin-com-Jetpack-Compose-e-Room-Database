package com.example.eduardo.RoomDataBase.Repositorios




import gestortarefas.bambi.eduardo.RoomDataBase.DAO.DoacaoDAO
import gestortarefas.bambi.eduardo.RoomDataBase.Enitdades.DoacaoEntity
import kotlinx.coroutines.flow.Flow

class DoacaoRepositorio(val doacaoDAO: DoacaoDAO) {

    suspend fun Adicionar(doacaoentity: DoacaoEntity) {
        doacaoDAO.Adicionar(doacaoentity)
    }

    suspend fun Actualizar(doacaoentity: DoacaoEntity) {
        doacaoDAO.Actualizar(doacaoentity)
    }
    suspend fun Eliminar(doacaoentity: DoacaoEntity) {
        doacaoDAO.Eliminar(doacaoentity)
    }

    fun ObterDoacaoporID(id: Int): Flow<DoacaoEntity?> {
        return doacaoDAO.ObterDoacaoporID(id)
    }
    fun ObterListaPorUsuario(idusuario: Int): Flow<List<DoacaoEntity>> {
        return doacaoDAO.ObterListaTudoPorIDUsuario(idusuario)
    }


}