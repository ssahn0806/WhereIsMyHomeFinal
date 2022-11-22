<template>
    <div>
        <b-modal size="xl"  :visible="openEmbed" @hide="setEmbed(false)" @close="setEmbed(false)" @ok="setEmbed(false)" @cancel="setEmbed(false)">
            <b-embed type="iframe" :src="url" allowfullscreen aspect="16by9"></b-embed>
        </b-modal>
    </div>
</template>

<script>
import Constant from "@/common/Constant.js";
import {mapGetters,mapMutations} from 'vuex';
    export default {
        computed: {
            ...mapGetters(["openEmbed","url"])
        },
        methods: {
            ...mapMutations([Constant.SET_EMBED])
        },

    }
</script>

<style scoped>

</style>