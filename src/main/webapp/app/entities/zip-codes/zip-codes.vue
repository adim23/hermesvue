<template>
  <div>
    <h2 id="page-heading" data-cy="ZipCodesHeading">
      <span v-text="$t('hermesvueApp.zipCodes.home.title')" id="zip-codes-heading">Zip Codes</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('hermesvueApp.zipCodes.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'ZipCodesCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-zip-codes"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('hermesvueApp.zipCodes.home.createLabel')"> Create a new Zip Codes </span>
          </button>
        </router-link>
      </div>
    </h2>
    <div class="row">
      <div class="col-sm-12">
        <form name="searchForm" class="form-inline" v-on:submit.prevent="search(currentSearch)">
          <div class="input-group w-100 mt-3">
            <input
              type="text"
              class="form-control"
              name="currentSearch"
              id="currentSearch"
              v-bind:placeholder="$t('hermesvueApp.zipCodes.home.search')"
              v-model="currentSearch"
            />
            <button type="button" id="launch-search" class="btn btn-primary" v-on:click="search(currentSearch)">
              <font-awesome-icon icon="search"></font-awesome-icon>
            </button>
            <button type="button" id="clear-search" class="btn btn-secondary" v-on:click="clear()" v-if="currentSearch">
              <font-awesome-icon icon="trash"></font-awesome-icon>
            </button>
          </div>
        </form>
      </div>
    </div>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && zipCodes && zipCodes.length === 0">
      <span v-text="$t('hermesvueApp.zipCodes.home.notFound')">No zipCodes found</span>
    </div>
    <div class="table-responsive" v-if="zipCodes && zipCodes.length > 0">
      <table class="table table-striped" aria-describedby="zipCodes">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('street')">
              <span v-text="$t('hermesvueApp.zipCodes.street')">Street</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'street'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('area')">
              <span v-text="$t('hermesvueApp.zipCodes.area')">Area</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'area'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fromNumber')">
              <span v-text="$t('hermesvueApp.zipCodes.fromNumber')">From Number</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fromNumber'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('toNumber')">
              <span v-text="$t('hermesvueApp.zipCodes.toNumber')">To Number</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'toNumber'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('country.name')">
              <span v-text="$t('hermesvueApp.zipCodes.country')">Country</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'country.name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('region.name')">
              <span v-text="$t('hermesvueApp.zipCodes.region')">Region</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'region.name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('city.name')">
              <span v-text="$t('hermesvueApp.zipCodes.city')">City</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'city.name'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="zipCodes in zipCodes" :key="zipCodes.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ZipCodesView', params: { zipCodesId: zipCodes.id } }">{{ zipCodes.id }}</router-link>
            </td>
            <td>{{ zipCodes.street }}</td>
            <td>{{ zipCodes.area }}</td>
            <td>{{ zipCodes.fromNumber }}</td>
            <td>{{ zipCodes.toNumber }}</td>
            <td>
              <div v-if="zipCodes.country">
                <router-link :to="{ name: 'CountriesView', params: { countriesId: zipCodes.country.id } }">{{
                  zipCodes.country.name
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="zipCodes.region">
                <router-link :to="{ name: 'RegionsView', params: { regionsId: zipCodes.region.id } }">{{
                  zipCodes.region.name
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="zipCodes.city">
                <router-link :to="{ name: 'CitiesView', params: { citiesId: zipCodes.city.id } }">{{ zipCodes.city.name }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ZipCodesView', params: { zipCodesId: zipCodes.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ZipCodesEdit', params: { zipCodesId: zipCodes.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(zipCodes)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="hermesvueApp.zipCodes.delete.question" data-cy="zipCodesDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-zipCodes-heading" v-text="$t('hermesvueApp.zipCodes.delete.question', { id: removeId })">
          Are you sure you want to delete this Zip Codes?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-zipCodes"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeZipCodes()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="zipCodes && zipCodes.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./zip-codes.component.ts"></script>
