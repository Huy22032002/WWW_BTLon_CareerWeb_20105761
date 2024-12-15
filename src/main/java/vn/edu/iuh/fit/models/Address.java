package vn.edu.iuh.fit.models;

import com.neovisionaries.i18n.CountryCode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "street", length = 150)
    private String street;

    @Column(name = "city", length = 50)
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "number", length = 20)
    private String number;

    @Column(name = "zipcode", length = 7)
    private String zipcode;
    // Quan hệ với Candidate
    @OneToOne(mappedBy = "address", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Candidate candidate;
    public Address(String s, String quangTrung, String hcm, String s1, CountryCode countryCode) {
    }

    public void setCountry(CountryCode country) {
        //chuyen country code sang string
        this.country = country.getAlpha2();
    }
}