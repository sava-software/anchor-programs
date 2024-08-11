package software.sava.anchor.programs.glam.anchor.types;

import java.lang.String;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt32LE;

public record CompanyModel(String fundGroupName, byte[] _fundGroupName,
                           String manCo, byte[] _manCo,
                           String domicileOfManCo, byte[] _domicileOfManCo,
                           String emailAddressOfManCo, byte[] _emailAddressOfManCo,
                           String fundWebsiteOfManCo, byte[] _fundWebsiteOfManCo) implements Borsh {


  public static CompanyModel createRecord(final String fundGroupName,
                                          final String manCo,
                                          final String domicileOfManCo,
                                          final String emailAddressOfManCo,
                                          final String fundWebsiteOfManCo) {
    return new CompanyModel(fundGroupName, Borsh.getBytes(fundGroupName),
                            manCo, Borsh.getBytes(manCo),
                            domicileOfManCo, Borsh.getBytes(domicileOfManCo),
                            emailAddressOfManCo, Borsh.getBytes(emailAddressOfManCo),
                            fundWebsiteOfManCo, Borsh.getBytes(fundWebsiteOfManCo));
  }

  public static CompanyModel read(final byte[] _data, final int offset) {
    int i = offset;
    final var fundGroupName = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (fundGroupName != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var manCo = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (manCo != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var domicileOfManCo = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (domicileOfManCo != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var emailAddressOfManCo = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (emailAddressOfManCo != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var fundWebsiteOfManCo = _data[i++] == 0 ? null : Borsh.string(_data, i);
    return new CompanyModel(fundGroupName, Borsh.getBytes(fundGroupName),
                            manCo, Borsh.getBytes(manCo),
                            domicileOfManCo, Borsh.getBytes(domicileOfManCo),
                            emailAddressOfManCo, Borsh.getBytes(emailAddressOfManCo),
                            fundWebsiteOfManCo, Borsh.getBytes(fundWebsiteOfManCo));
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeOptional(_fundGroupName, _data, i);
    i += Borsh.writeOptional(_manCo, _data, i);
    i += Borsh.writeOptional(_domicileOfManCo, _data, i);
    i += Borsh.writeOptional(_emailAddressOfManCo, _data, i);
    i += Borsh.writeOptional(_fundWebsiteOfManCo, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.lenOptional(_fundGroupName)
         + Borsh.lenOptional(_manCo)
         + Borsh.lenOptional(_domicileOfManCo)
         + Borsh.lenOptional(_emailAddressOfManCo)
         + Borsh.lenOptional(_fundWebsiteOfManCo);
  }
}
