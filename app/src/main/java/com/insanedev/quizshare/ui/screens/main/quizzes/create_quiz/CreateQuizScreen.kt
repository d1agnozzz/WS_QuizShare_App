package com.insanedev.quizshare.ui.screens.main.quizzes.create_quiz

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.insanedev.quizshare.R
import com.insanedev.quizshare.ui.components.QTextField
import com.insanedev.quizshare.ui.components.QuestionCard
import com.insanedev.quizshare.ui.components.TopAppBarComposable
import com.insanedev.quizshare.ui.screens.main.quizzes.create_quiz.models.CreateQuizEvent
import com.insanedev.quizshare.ui.screens.main.quizzes.create_quiz.models.CreateQuizViewState
import com.insanedev.quizshare.ui.theme.AppTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateQuizScreen(
    onBackClick: () -> Unit,
    viewModel: CreateQuizViewModel
) {

    val viewState = viewModel.viewState.observeAsState(CreateQuizViewState())

    Scaffold(
        topBar = {
            TopAppBarComposable(
                title = stringResource(R.string.quiz_creation),
                onBackClick = onBackClick
            )
        },
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = {
                        viewModel.obtainEvent(CreateQuizEvent.QuizDone)
                    }) {
                        Icon(Icons.Filled.DoneAll, contentDescription = "")
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            viewModel.obtainEvent(CreateQuizEvent.AddQuestionClicked)
                        },
                        containerColor = AppTheme.colors.primaryAccent,
                        contentColor = Color.White,
                        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                    ) {
                        Icon(Icons.Filled.Add, "Localized description")
                    }
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .padding(8.dp)
        ) {
            item {
                Column()
                {
                    QTextField(
                        value = viewState.value.titleValue,
                        label = stringResource(id = R.string.quiz_title),
                        onTextFieldChange = {
                            viewModel.obtainEvent(CreateQuizEvent.QuizTitleChanged(it))
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            letterSpacing = 0.5.sp
                        ),
                        value = "",
                        onValueChange = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp),
                        label = {
                            Text(
                                text = stringResource(R.string.quiz_description),
                                style = TextStyle(
                                    color = AppTheme.colors.secondaryText
                                ),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                letterSpacing = 0.1.sp
                            )
                        },
                        singleLine = false,
                        maxLines = 5,
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = AppTheme.colors.textFieldUnfocusedBorder,
                            focusedBorderColor = AppTheme.colors.primaryAccent,
                            focusedLabelColor = AppTheme.colors.primaryAccent


                        ),
                    )
                    QTextField(
                        value = "",
                        label = stringResource(id = R.string.quiz_group),
                        onTextFieldChange = {},
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(64.dp))
                }
            }

            items(items = viewState.value.questionMap.toList()) { pair ->
                QuestionCard(
                    questionNumber = pair.first.questionNumber,
                    questionTitle = pair.first.title,
                    questionType = pair.first.type,
                    onQuestionTitleChanged = { questionNumber, value ->
                        viewModel.obtainEvent(
                            CreateQuizEvent.QuestionTitleChanged(
                                questionNumber,
                                value
                            )
                        )
                    },
                    answers = pair.second,
                    onAnswerOptionClicked = { questionNumber, answerId ->
                        viewModel.obtainEvent(
                            CreateQuizEvent.AnswerOptionClicked(
                                questionNumber,
                                answerId
                            )
                        )
                    },
                    onAnswerContentChanged = { questionNumber, answerId, value ->
                        viewModel.obtainEvent(
                            CreateQuizEvent.AnswerContentChanged(
                                questionNumber,
                                answerId,
                                value
                            )
                        )
                    },
                    onAddAnswerClicked = { questionNumber ->
                        viewModel.obtainEvent(
                            CreateQuizEvent.AddAnswerClicked(
                                questionNumber
                            )
                        )
                    },
                    onRemoveQuestionClicked = { questionNumber ->
                        viewModel.obtainEvent(
                            CreateQuizEvent.RemoveQuestionClicked(
                                questionNumber
                            )
                        )
                    }
                )
            }
        }
    }

}