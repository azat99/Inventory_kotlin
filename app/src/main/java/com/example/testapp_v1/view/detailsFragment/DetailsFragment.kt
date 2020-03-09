package com.example.testapp_v1.view.detailsFragment


import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp_v1.R
import com.example.testapp_v1.model.detailEntity.Details
import com.example.testapp_v1.viewmodel.DetailsViewModel
import kotlinx.android.synthetic.main.fragment_details.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*


class DetailsFragment : Fragment() {

    private val detailsViewModel: DetailsViewModel by viewModel()

    private var documentID: String? = null
    private var layoutGoneArgument: String? = null
    private var title: String? = null

    private lateinit var detailsAdapter: DetailsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)

        detailsAdapter = DetailsAdapter(arrayListOf(), null)
        with(details_recycler_view) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = detailsAdapter
        }

        arguments?.let {
            documentID = DetailsFragmentArgs.fromBundle(it).string
            title = DetailsFragmentArgs.fromBundle(it).title
            layoutGoneArgument = DetailsFragmentArgs.fromBundle(it).followDetails
        }

        activity?.title = title

        if (layoutGoneArgument != null) {
            editText_LinerLayout.visibility = View.GONE
            linear_layout_view.visibility = View.VISIBLE
            detailsViewModel.getAllBarcode(documentID.toString()).observe(viewLifecycleOwner, Observer {
                Collections.reverse(it)
                detailsAdapter.showCheckedBarcodes(barcode = it)
            })
        }

        // проверяем есть ли в базе данные которые нам нужны
        detailsViewModel.foundLiveData.observe(viewLifecycleOwner, Observer { tds ->
            if (tds == null) {
                not_found_barcode_rl?.visibility = View.VISIBLE
                not_found_barcode.text = detailsViewModel.text + " такого баркода в базе нет!"
            } else {
                not_found_barcode_rl?.visibility = View.GONE
                detailsAdapter.showFoundBarcode(tds = tds)
                detailsViewModel.checkCurrentOwnerName(detailsViewModel.text, documentID.toString())
                    .observe(viewLifecycleOwner, Observer {
                        if (it?.name == null) {
                            detailsViewModel.updateOwnerName(
                                detailsViewModel.text,
                                tds.name.toString()
                            )
                        }
                    })
            }
        })

        // если такого баркода в базе нету то добавляем его в базу
        detailsViewModel.tekseru.observe(viewLifecycleOwner, Observer { size ->
            if (size == 0) {
                /// добавить как проверено
                detailsViewModel.putAllBarcodeToDetails(
                    Details(
                        p_id = documentID.toString(),
                        barcode = detailsViewModel.text,
                        comment = null,
                        name = null
                    )
                )
                detailsViewModel.updateCounter(documentID.toString())
            }
        })
        bar_code_editText.requestFocus()
        bar_code_editText.doAfterTextChanged { text ->
            if (text?.length == 13) {
                detailsViewModel.text = text.toString()
                // поиск баркодов по базе
                detailsViewModel.onSearchPressedWith(text = text.toString())
                // проверяем есть ли в базе так такие баркоды
                detailsViewModel.proverkaNaPodlinnost(text.toString(), documentID.toString())
                //
                detailsViewModel.getCurrentComment(detailsViewModel.text, documentID.toString())

                bar_code_editText.text.clear()
                bar_code_editText.requestFocus()

                keyBoardDown()
            }
        }

        detailsViewModel.comment.observe(viewLifecycleOwner, Observer {
            if (it == null) {
                comment_editText.setText("")
            } else {
                comment_editText.setText(it.toString())
            }

        })

        comment_button.setOnClickListener {
            if (comment_editText.text.isNotEmpty() && detailsViewModel.text.length == 13) {
                var commentText = comment_editText.text.toString()
                detailsViewModel.updateCommentFromBarcode(
                    detailsViewModel.text,
                    documentID.toString(),
                    comment_editText.text.toString()
                )
                detailsViewModel.checkCurrentOwnerName(detailsViewModel.text, documentID.toString())
                    .observe(viewLifecycleOwner, Observer {
                        if (it.name == null) {
                            detailsViewModel.updateOwnerName(
                                detailsViewModel.text,
                                commentText
                            )
                        }
                    })

                Toast.makeText(context, "Успешно добавлено!", Toast.LENGTH_SHORT).show()
            } else {
                if (detailsViewModel.text.length != 13) {
                    bar_code_editText.error = "Размер чисел должно быть 13"
                } else {
                    comment_editText.error = "Напищи что нибудь"
                }
            }
            keyBoardDown()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
    }

    private fun keyBoardDown() {
        val inputManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            activity?.currentFocus?.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

}
