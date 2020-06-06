package rest.model

import javax.persistence.*

@Entity
@Table(name = "person")
class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
    var firstname: String? = null
    var lastname: String? = null

    @OneToOne(cascade = [CascadeType.ALL])
    var phone: Phone? = null

    constructor() {}
    constructor(firstname: String?, lastname: String?) {
        this.firstname = firstname
        this.lastname = lastname
    }

    constructor(id: Long?, firstname: String?, lastname: String?) {
        this.id = id
        this.firstname = firstname
        this.lastname = lastname
    }

}