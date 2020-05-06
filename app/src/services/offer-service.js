import axiosWrapper from '../providers/axios-wrapper'

// https://marmelab.com/react-admin/DataProviders.html#writing-your-own-data-provider
export default{
    getList: async (params)=>{
        const { page, perPage } = params.pagination;
        const { field, order } = params.sort;
        const query = {
            sort: JSON.stringify([field, order]),
            range: JSON.stringify([(page - 1) * perPage, page * perPage - 1]),
            filter: JSON.stringify(params.filter),
        };
        return axiosWrapper({
            method: 'post',
            url: `${process.env.OFFER_URL}/list`,
            data: query
        }).then(response=>response.data)
    }
}