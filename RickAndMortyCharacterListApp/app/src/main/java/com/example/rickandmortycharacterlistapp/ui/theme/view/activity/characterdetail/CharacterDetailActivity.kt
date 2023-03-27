package com.example.rickandmortycharacterlistapp.ui.theme.view.activity.characterdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.example.rickandmortycharacterlistapp.R
import com.example.rickandmortycharacterlistapp.domain.model.Character
import com.example.rickandmortycharacterlistapp.ui.theme.BackgroundGreen
import com.example.rickandmortycharacterlistapp.ui.theme.LightGreen
import com.example.rickandmortycharacterlistapp.ui.theme.RickAndMortyCharacterListAppTheme

class CharacterDetailActivity : ComponentActivity() {

    companion object {

        private const val CHARACTER = "CHARACTER"

        fun createIntent(context: Context, character: Character) =
            Intent(context, CharacterDetailActivity::class.java).apply {
                putExtra(CHARACTER, character)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickAndMortyCharacterListAppTheme {
                intent.getParcelableExtra<Character>(CHARACTER)?.let {
                    Detail(it)
                }
            }
        }
    }

    @Composable
    fun Detail(character: Character) {
        Column(
            modifier = Modifier
                .background(BackgroundGreen)
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Top,
            content = {
                TopAppBar(character)
                CharacterImage(character)
                Fields(character)
            }
        )

    }

    @Composable
    private fun TopAppBar(character: Character) {
        TopAppBar(
            title = { Text(text = character.name) },
            navigationIcon = {
                IconButton(onClick = { finish() }) {
                    Icon(Icons.Default.ArrowBack, null)
                }
            })
    }

    @Composable
    private fun CharacterImage(character: Character) {
        Box(
            Modifier
                .fillMaxWidth()
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = rememberImagePainter(
                    data = character.image,
                    builder = { scale(Scale.FILL).transformations(CircleCropTransformation()) }),
                contentDescription = ""
            )
        }
    }

    @Composable
    private fun Field(
        text: String,
        alignment: Alignment = Alignment.CenterStart,
        background: Color = BackgroundGreen
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(background),
            contentAlignment = alignment
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Medium
            )
        }
    }

    @Composable
    private fun Fields(character: Character) {
        Field(stringResource(R.string.status))
        Field(character.status, Alignment.Center, LightGreen)
        Field(stringResource(R.string.specie))
        Field(character.species, Alignment.Center, LightGreen)
        Field(stringResource(R.string.gender))
        Field(character.gender, Alignment.Center, LightGreen)
    }
}

