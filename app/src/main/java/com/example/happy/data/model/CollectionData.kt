package com.example.happy.data.model

import com.example.happy.model.CollectionData
import com.example.happy.network.dto.CollectionEntity

internal fun CollectionEntity.toCollectionData() = CollectionData(
    category = category!!,
    manageYear = manageYear!!,
    titleKor = titleKor!!,
    titleEng = titleEng!!,
    standard = standard!!,
    madeYear = madeYear!!,
    technic = technic!!,
    productDetail = productDetail!!,
    writerName = writerName!!,
    mainUri = mainUri!!,
    thumbUri = thumbUri!!,
)