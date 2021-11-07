package br.com.ymc.gamesave.viewModels

import br.com.ymc.gamesave.ui.games.domain.use_case.api_use_case.GetGamesUseCaseTest
import br.com.ymc.gamesave.ui.games.domain.use_case.api_use_case.SearchGamesUseCaseTest
import org.junit.Before

class AllGamesViewModelTest {
    private lateinit var viewModel: AllGamesViewModel

    @Before
    fun setup()
    {
        viewModel = AllGamesViewModel(GetGamesUseCaseTest(), SearchGamesUseCaseTest())
    }

}