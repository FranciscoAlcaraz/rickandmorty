package com.example.rickandmortycharacterlistapp.ui.theme.view.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import coil.compose.rememberImagePainter
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.example.rickandmortycharacterlistapp.R
import com.example.rickandmortycharacterlistapp.domain.model.Character
import com.example.rickandmortycharacterlistapp.ui.theme.RickAndMortyCharacterListAppTheme
import com.example.rickandmortycharacterlistapp.ui.theme.view.activity.characterdetail.CharacterDetailActivity
import com.example.rickandmortycharacterlistapp.viewmodel.view.characterlist.CharacterListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: CharacterListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickAndMortyCharacterListAppTheme {
                val characters by viewModel.characters
                CharacterList(characters)
            }
        }
        observeData()
        viewModel.fetchItems()
    }

    private fun observeData() {
        viewModel.showError.observe(this) {
            Toast.makeText(
                this@MainActivity,
                getString(R.string.generic_error),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    @Composable
    private fun CharacterList(characters: List<Character>) {
        Column {
            FiltersSection()
            val listState = rememberLazyListState()
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                state = listState
            ) {
                items(characters.size) {
                    CharacterListItem(character = characters[it])
                }
            }

            listState.OnBottomReached {
                viewModel.fetchItems()
            }
        }
    }

    @Composable
    private fun FiltersSection() {
        var areFiltersVisible by remember { mutableStateOf(false) }

        val onFiltersClick = {
            areFiltersVisible = !areFiltersVisible
            viewModel.resetSearch()
        }
        Row(
            Modifier.clickable { onFiltersClick.invoke() },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            IconButton(onClick = { onFiltersClick.invoke() }) {
                Icon(Icons.Default.Search, contentDescription = null)
            }
            Text(text = stringResource(R.string.filters))
        }
        Filters(areFiltersVisible)
    }

    @Composable
    private fun Filters(areFiltersVisible: Boolean) {
        if (areFiltersVisible) {
            SearchBar(
                label = stringResource(R.string.name),
                onSearch = { viewModel.onNameFilterChanged(it) })
            SearchBar(
                label = stringResource(R.string.type),
                onSearch = { viewModel.onTypeFilterChanged(it) })
            SearchBar(
                label = stringResource(R.string.specie),
                onSearch = { viewModel.onSpecieFilterChanged(it) })
            DropDownFilterMenu(stringResource(R.string.status),
                resources.getStringArray(R.array.status_list).toList(),
                { viewModel.onStatusFilterChanged(it) },
                { viewModel.clearStatusFilter() }
            )
            DropDownFilterMenu(
                stringResource(R.string.gender),
                resources.getStringArray(R.array.gender_list).toList(),
                { viewModel.onGenderFilterChanged(it) },
                { viewModel.clearGenderFilter() }
            )
        }
    }

    @Composable
    private fun CharacterListItem(character: Character) {
        val backgroundColor = MaterialTheme.colors.primary
        Card(
            modifier = Modifier
                .padding(8.dp, 4.dp)
                .fillMaxWidth()
                .clickable {
                    startActivity(CharacterDetailActivity.createIntent(this, character))
                }
                .height(110.dp), shape = RoundedCornerShape(8.dp), elevation = 4.dp
        ) {
            Surface(color = backgroundColor) {
                CharacterItemRow(character)
            }
        }
    }

    @Composable
    private fun CharacterItemRow(character: Character) {
        Row(
            Modifier
                .padding(4.dp)
                .fillMaxSize()
        ) {
            Image(
                painter = rememberImagePainter(
                    data = character.image,
                    builder = { scale(Scale.FILL).transformations(CircleCropTransformation()) }),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(10.dp)
                    .weight(0.1f)
            )

            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxHeight()
                    .weight(0.2f)
            ) {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

    @Composable
    private fun DropDownFilterMenu(
        label: String,
        options: List<String>,
        onDropDownOptionSelected: (String) -> Unit,
        onFilterCleared: () -> Unit
    ) {
        var isExpanded by remember { mutableStateOf(false) }

        var selectedValue by remember { mutableStateOf(label) }

        var textFieldSize by remember { mutableStateOf(Size.Zero) }

        val icon = if (isExpanded)
            Icons.Filled.KeyboardArrowUp
        else
            Icons.Filled.KeyboardArrowDown

        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(
                Modifier
                    .padding(5.dp)
                    .wrapContentWidth()
            ) {
                OutlinedTextField(
                    value = selectedValue,
                    onValueChange = { selectedValue = it },
                    modifier = Modifier
                        .clickable(false, null, null) { }
                        .wrapContentWidth()
                        .onGloballyPositioned { coordinates ->
                            textFieldSize = coordinates.size.toSize()
                        },
                    label = { Text(label) },
                    trailingIcon = {
                        Icon(icon, null,
                            Modifier.clickable { isExpanded = !isExpanded })
                    }
                )

                DropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false },
                    modifier = Modifier
                        .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
                ) {
                    options.forEach { label ->
                        DropdownMenuItem(onClick = {
                            onDropDownOptionSelected.invoke(label)
                            selectedValue = label
                            isExpanded = false
                        }) {
                            Text(text = label)
                        }
                    }
                }
            }

            IconButton(onClick = {
                selectedValue = label
                onFilterCleared.invoke()
            }) {
                Icon(Icons.Default.Close, contentDescription = null)
            }
        }
    }

    @Composable
    private fun SearchBar(
        modifier: Modifier = Modifier,
        label: String,
        onSearch: (String) -> Unit
    ) {
        var query by remember { mutableStateOf(TextFieldValue()) }

        Box(modifier = modifier.padding(5.dp)) {
            TextField(
                value = query,
                onValueChange = {
                    query = it
                    onSearch(it.text)
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(label) },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White
                ),
                leadingIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            Icons.Filled.Search,
                            contentDescription = null
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearch(query.text)
                    }
                )
            )
        }
    }
}

@Composable
fun LazyListState.OnBottomReached(
    loadMore: () -> Unit
) {
    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
                ?: return@derivedStateOf true

            lastVisibleItem.index == layoutInfo.totalItemsCount - 1
        }
    }

    LaunchedEffect(shouldLoadMore) {
        snapshotFlow { shouldLoadMore.value }.collect { if (it) loadMore() }
    }
}