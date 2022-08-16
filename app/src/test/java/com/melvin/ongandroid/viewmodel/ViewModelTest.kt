package com.melvin.ongandroid.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.melvin.ongandroid.businesslogic.*
import com.melvin.ongandroid.model.APIResponse
import com.melvin.ongandroid.model.contact.ContactDataModel
import com.melvin.ongandroid.model.login.LoginRepository
import com.melvin.ongandroid.model.signUpNewUser.*
import com.melvin.ongandroid.model.slides.SlidesDataModel
import com.melvin.ongandroid.model.staff.StaffDataModel
import com.melvin.ongandroid.model.testimonials.DataModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ViewModelTest {

    @MockK
    private lateinit var getTestimonialsUseCase: GetTestimonialsUseCase

    @MockK
    private lateinit var getSlidesUseCase: GetSlidesUseCase

    @MockK
    private lateinit var getStaffUseCase: GetStaffUseCase

    @MockK
    private lateinit var getActivitiesUseCase: GetActivitiesUseCase

    @MockK
    private lateinit var getNewsUserCase: GetNewsUseCase

    @MockK
    private lateinit var sendContactUseCase: SendContactUsesCase

    @MockK
    private lateinit var loginRepository: LoginRepository

    @MockK
    private lateinit var sendNewUserUseCase: SendNewUserUseCase

    @MockK
    private lateinit var newUserRepository: NewUserRepository

    private lateinit var viewModel: ViewModel

    @get: Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = ViewModel(getTestimonialsUseCase,
            getSlidesUseCase,
            getStaffUseCase,
            getActivitiesUseCase,
            getNewsUserCase,
        sendContactUseCase,
            sendNewUserUseCase,
            loginRepository)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when onLoadTestimonials is called get list of dataModel `() = runTest {
        //GIVEN
        val listOfDataModel = LIST_DATA_MODEL
        coEvery { getTestimonialsUseCase() } returns listOfDataModel

        //WHEN
        viewModel.onLoadTestimonials()

        //THEN
        assert(viewModel.testimonials.value == listOfDataModel)
        assert(viewModel.testimonialStatus.value == Status.SUCCESS)
    }

    @Test
    fun `when onLoadTestimonials is called get emptyList of dataModel`() = runTest {
        //GIVEN
        val listOfDataModel = emptyList<DataModel>()
        coEvery { getTestimonialsUseCase() } returns listOfDataModel

        //WHEN
        viewModel.onLoadTestimonials()

        //THEN
        assert(viewModel.testimonialStatus.value == Status.ERROR)
    }

    @Test
    fun `when onCreateSlides is called get list of slidesDataModel `() = runTest {
        //GIVEN
        val listOfSlidesDataModel = LIST__SLIDE_DATA_MODEL
        coEvery { getSlidesUseCase() } returns listOfSlidesDataModel

        //WHEN
        viewModel.onCreateSlides()

        //THEN
        assert(viewModel.slidesModel.value == listOfSlidesDataModel)
        assert(viewModel.slideStatus.value == Status.SUCCESS)
        assert(viewModel.slidesCallFailed.value == false)
    }

    @Test
    fun `when onCreateSlides is called get emptyList of slidesDataModel`() = runTest {
        //GIVEN
        val listOfSlidesDataModel = emptyList<SlidesDataModel>()
        coEvery { getSlidesUseCase() } returns listOfSlidesDataModel

        //WHEN
        viewModel.onCreateSlides()

        //THEN
        assert(viewModel.slideStatus.value == Status.ERROR)
        assert(viewModel.slidesCallFailed.value == true)

    }

    @Test
    fun `is refresh() call when onLoadTestimonials() and onCreateSlides() successfully`() =
        runTest {
            //GIVEN
            val listOfDataModel = LIST_DATA_MODEL
            coEvery { getTestimonialsUseCase() } returns listOfDataModel

            val listOfSlidesDataModel = LIST__SLIDE_DATA_MODEL
            coEvery { getSlidesUseCase() } returns listOfSlidesDataModel

            //WHEN
            viewModel.refresh()

            //THEN
            assert(viewModel.testimonials.value == listOfDataModel)
            assert(viewModel.testimonialStatus.value == Status.SUCCESS)
            assert(viewModel.slidesModel.value == listOfSlidesDataModel)
            assert(viewModel.slideStatus.value == Status.SUCCESS)
            assert(viewModel.slidesCallFailed.value == false)
        }

    @Test
    fun `is refresh() call when onLoadTestimonials() successfully and onCreateSlides() failed`() =
        runTest {
            //GIVEN
            val listOfDataModel = LIST_DATA_MODEL
            coEvery { getTestimonialsUseCase() } returns listOfDataModel

            val listOfSlidesDataModel = emptyList<SlidesDataModel>()
            coEvery { getSlidesUseCase() } returns listOfSlidesDataModel

            //WHEN
            viewModel.refresh()

            //THEN
            assert(viewModel.testimonials.value == listOfDataModel)
            assert(viewModel.testimonialStatus.value == Status.SUCCESS)
            assert(viewModel.slideStatus.value == Status.ERROR)
            assert(viewModel.slidesCallFailed.value == true)
        }

    @Test
    fun `is refresh() call when onLoadTestimonials() failed and onCreateSlides() successfully`() =
        runTest {
            //GIVEN
            val listOfDataModel = emptyList<DataModel>()
            coEvery { getTestimonialsUseCase() } returns listOfDataModel

            val listOfSlidesDataModel = LIST__SLIDE_DATA_MODEL
            coEvery { getSlidesUseCase() } returns listOfSlidesDataModel

            //WHEN
            viewModel.refresh()

            //THEN
            assert(viewModel.testimonialStatus.value == Status.ERROR)
            assert(viewModel.slidesModel.value == listOfSlidesDataModel)
            assert(viewModel.slideStatus.value == Status.SUCCESS)
            assert(viewModel.slidesCallFailed.value == false)
        }

    @Test
    fun `is refresh() call when onLoadTestimonials() and onCreateSlides() failed`() = runTest {
        //GIVEN
        val listOfDataModel = emptyList<DataModel>()
        coEvery { getTestimonialsUseCase() } returns listOfDataModel

        val listOfSlidesDataModel = emptyList<SlidesDataModel>()
        coEvery { getSlidesUseCase() } returns listOfSlidesDataModel

        //WHEN
        viewModel.refresh()

        //THEN
        assert(viewModel.testimonialStatus.value == Status.ERROR)
        assert(viewModel.slideStatus.value == Status.ERROR)
        assert(viewModel.slidesCallFailed.value == true)
    }

    @Test
    fun `when onCreateStaff recovers a staff list and set on the _staff (liveData)`() = runTest {
        //GIVEN
        val listOfStaffDataModel = listOf(StaffDataModel(1,
            "Andres",
            "imagen",
            "descripcion",
            "facebookUrl",
            "linkedinUrl",
            null,
            null,
            null,
            0))
        coEvery { getStaffUseCase() } returns listOfStaffDataModel

        //WHEN
        viewModel.onCreateStaff()

        //THEN
        assert(viewModel.staffStatus.value == Status.SUCCESS)
        assert(viewModel.staff.value == listOfStaffDataModel)
    }

    @Test
    fun `when onCreateStaff recovers a staff empty list from getStaffUseCase`() = runTest {
        //GIVEN
        val emptyListOfStaffDataModel = emptyList<StaffDataModel>()
        coEvery { getStaffUseCase() } returns emptyListOfStaffDataModel

        //WHEN
        viewModel.onCreateStaff()

        //THEN
        assert(viewModel.staffStatus.value == Status.ERROR)
    }

    companion object {
        private val ITEM_1 = DataModel(1,
            "dataModel One",
            "http:thiIsAnImage",
            "dataModel description one",
            null,
            null,
            null,
            null)
        private val ITEM_2 =
            DataModel(2,
                "dataModel two",
                "http:thiIsAnImage",
                "datamMdel description two",
                null,
                null,
                null,
                null)
        private val ITEM_3 =
            DataModel(3,
                "dataModel three",
                "http:thiIsAnImage",
                "dataModel description three",
                null,
                null,
                null,
                null)
        private val LIST_DATA_MODEL = listOf(ITEM_1, ITEM_2, ITEM_3)

        private val ITEM_SLIDE_1 = SlidesDataModel(1,
            "slidesDataModel One",
            "slidesDataModel description one",
            "http:thiIsAnImage",
            null,
            null,
            null,
            null,
            null,
            null)
        private val ITEM_SLIDE_2 =
            SlidesDataModel(2,
                "slidesDataModel two",
                "slidesDataModel description two",
                "http:thiIsAnImage",
                null,
                null,
                null,
                null,
                null,
                null)
        private val ITEM_SLIDE_3 =
            SlidesDataModel(3,
                "slidesDataModel three",
                "slidesDataModel description three",
                "http:thiIsAnImage",
                null,
                null,
                null,
                null,
                null,
                null)
        private val LIST__SLIDE_DATA_MODEL = listOf(ITEM_SLIDE_1, ITEM_SLIDE_2, ITEM_SLIDE_3)
        private val CONTACT_FORM =
            ContactDataModel(
                0,
                "personName",
                "personName@email.com",
                "some random message"
            )
    }
    @Test
    fun `when the body have the correct format `() = runTest {
        //GIVEN
        val listOfDataModel = CONTACT_FORM

        //WHEN
        viewModel.setContactName(listOfDataModel.name)
        viewModel.setContactMail(listOfDataModel.email)
        viewModel.setContactMessage(listOfDataModel.message)
        viewModel.validateDataContact()

        //THEN
        assert(viewModel.isButtonEnabled.value == true)
    }

    @Test
    fun `when the contacts name doesn't have the correct format `() = runTest {
        //GIVEN
        val listOfDataModel = ContactDataModel(0,"","personName@email.com", "some random message")
        //WHEN
        viewModel.setContactName(listOfDataModel.name)
        viewModel.setContactMail(listOfDataModel.email)
        viewModel.setContactMessage(listOfDataModel.message)
        viewModel.validateDataContact()

        //THEN
        assert(viewModel.isButtonEnabled.value == false)
    }
    @Test
    fun `when the contacts mail doesn't have the correct format `() = runTest {
        //GIVEN
        val listOfDataModel = ContactDataModel(0,"personName","", "some random message")
        //WHEN
        viewModel.setContactName(listOfDataModel.name)
        viewModel.setContactMail(listOfDataModel.email)
        viewModel.setContactMessage(listOfDataModel.message)
        viewModel.validateDataContact()

        //THEN
        assert(viewModel.isButtonEnabled.value == false)
    }
    @Test
    fun `when the contacts message doesn't have the correct format `() = runTest {
        //GIVEN
        val listOfDataModel = ContactDataModel(0,"personName","personName@email.com", "")
        //WHEN
        viewModel.setContactName(listOfDataModel.name)
        viewModel.setContactMail(listOfDataModel.email)
        viewModel.setContactMessage(listOfDataModel.message)
        viewModel.validateDataContact()

        //THEN
        assert(viewModel.isButtonEnabled.value == false)
    }

    @Test
    fun `when sendContactDAte get a success as response `() = runTest {
        //GIVEN
        val listOfDataModel = CONTACT_FORM
        coEvery { sendContactUseCase(listOfDataModel) } returns true

        //WHEN
        viewModel.setContactName(listOfDataModel.name)
        viewModel.setContactMail(listOfDataModel.email)
        viewModel.setContactMessage(listOfDataModel.message)
        viewModel.sendContactDate()

        //THEN
        assert(viewModel.sendContactStatus.value == Status.SUCCESS)
    }


//    Sign Up ViewModel Test

    @Test
    fun `When viewModel is send a new User at the first time, get all Users and set the first value` () = runTest {

        // Given
        val newUser = NewUserBodyModel("alkemy", "alkemy@gmail.com", "#1alkemyK")
        val newUserResponse = NewUserResponse(true, " ")
        coEvery { sendNewUserUseCase(newUser) } returns APIResponse(success = true, data = newUserResponse, null)

        // When
           viewModel.sendNewUser(newUser.nameNewUser,newUser.emailNewUser, newUser.passwordNewUser)

        // Then
            assert(viewModel.statusSignUpNewUser.value == Status.SUCCESS)

    }

    @Test
    fun `When the name is empty` () = runTest {

        // Given
        val newUser = NewUserBodyModel(" ", "alkemy@gmail.com", "#1alkemyK")
        val newUserResponseError = NewUserResponseError("Error", NewUserResponseErrorBody())
        coEvery { sendNewUserUseCase(newUser) } returns APIResponse(success = false, data = null, newUserResponseError)

        // When
        viewModel.sendNewUser(newUser.nameNewUser,newUser.emailNewUser, newUser.passwordNewUser)

        // Then
        assert(viewModel.statusSignUpNewUser.value == Status.ERROR)

    }


    @Test
    fun `When the email is invalid` () = runTest {

        // Given
        val newUser = NewUserBodyModel("alkemy", " ", "#1alkemyK")
        val newUserResponseError = NewUserResponseError("Error", NewUserResponseErrorBody())
        coEvery { sendNewUserUseCase(newUser) } returns APIResponse(success = false, data = null, newUserResponseError)

        // When
        viewModel.sendNewUser(newUser.nameNewUser,newUser.emailNewUser, newUser.passwordNewUser)

        // Then
        assert(viewModel.statusSignUpNewUser.value == Status.ERROR)

    }


    @Test
    fun `When the password is invalid` () = runTest {
        // Given
        val newUser = NewUserBodyModel("alkemy", "alkemy@gmail.com", " ")
        val newUserResponseError = NewUserResponseError("Error", NewUserResponseErrorBody())
        coEvery { sendNewUserUseCase(newUser) } returns APIResponse(success = false, data = null, newUserResponseError)

        // When
        viewModel.sendNewUser(newUser.nameNewUser,newUser.emailNewUser, newUser.passwordNewUser)

        // Then
        assert(viewModel.statusSignUpNewUser.value == Status.ERROR)

    }


}



