package software.sava.anchor.programs.glam.anchor.types;

import java.lang.Boolean;
import java.lang.String;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;

public record FundModel(PublicKey id,
                        String name, byte[] _name,
                        String uri, byte[] _uri,
                        String openfundsUri, byte[] _openfundsUri,
                        Boolean isEnabled,
                        PublicKey[] assets,
                        int[] assetsWeights,
                        ShareClassModel[] shareClasses,
                        CompanyModel company,
                        ManagerModel manager,
                        CreatedModel created,
                        DelegateAcl[] delegateAcls,
                        IntegrationAcl[] integrationAcls,
                        Boolean isRawOpenfunds,
                        FundOpenfundsModel rawOpenfunds) implements Borsh {

  public static FundModel createRecord(final PublicKey id,
                                       final String name,
                                       final String uri,
                                       final String openfundsUri,
                                       final Boolean isEnabled,
                                       final PublicKey[] assets,
                                       final int[] assetsWeights,
                                       final ShareClassModel[] shareClasses,
                                       final CompanyModel company,
                                       final ManagerModel manager,
                                       final CreatedModel created,
                                       final DelegateAcl[] delegateAcls,
                                       final IntegrationAcl[] integrationAcls,
                                       final Boolean isRawOpenfunds,
                                       final FundOpenfundsModel rawOpenfunds) {
    return new FundModel(id,
                         name, Borsh.getBytes(name),
                         uri, Borsh.getBytes(uri),
                         openfundsUri, Borsh.getBytes(openfundsUri),
                         isEnabled,
                         assets,
                         assetsWeights,
                         shareClasses,
                         company,
                         manager,
                         created,
                         delegateAcls,
                         integrationAcls,
                         isRawOpenfunds,
                         rawOpenfunds);
  }

  public static FundModel read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var id = _data[i++] == 0 ? null : readPubKey(_data, i);
    if (id != null) {
      i += 32;
    }
    final var name = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (name != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var uri = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (uri != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var openfundsUri = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (openfundsUri != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var isEnabled = _data[i++] == 0 ? null : _data[i] == 1;
    if (isEnabled != null) {
      ++i;
    }
    final var assets = Borsh.readPublicKeyVector(_data, i);
    i += Borsh.len(assets);
    final var assetsWeights = Borsh.readintVector(_data, i);
    i += Borsh.len(assetsWeights);
    final var shareClasses = Borsh.readVector(ShareClassModel.class, ShareClassModel::read, _data, i);
    i += Borsh.len(shareClasses);
    final var company = _data[i++] == 0 ? null : CompanyModel.read(_data, i);
    if (company != null) {
      i += Borsh.len(company);
    }
    final var manager = _data[i++] == 0 ? null : ManagerModel.read(_data, i);
    if (manager != null) {
      i += Borsh.len(manager);
    }
    final var created = _data[i++] == 0 ? null : CreatedModel.read(_data, i);
    if (created != null) {
      i += Borsh.len(created);
    }
    final var delegateAcls = Borsh.readVector(DelegateAcl.class, DelegateAcl::read, _data, i);
    i += Borsh.len(delegateAcls);
    final var integrationAcls = Borsh.readVector(IntegrationAcl.class, IntegrationAcl::read, _data, i);
    i += Borsh.len(integrationAcls);
    final var isRawOpenfunds = _data[i++] == 0 ? null : _data[i] == 1;
    if (isRawOpenfunds != null) {
      ++i;
    }
    final var rawOpenfunds = _data[i++] == 0 ? null : FundOpenfundsModel.read(_data, i);
    return new FundModel(id,
                         name, Borsh.getBytes(name),
                         uri, Borsh.getBytes(uri),
                         openfundsUri, Borsh.getBytes(openfundsUri),
                         isEnabled,
                         assets,
                         assetsWeights,
                         shareClasses,
                         company,
                         manager,
                         created,
                         delegateAcls,
                         integrationAcls,
                         isRawOpenfunds,
                         rawOpenfunds);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeOptional(id, _data, i);
    i += Borsh.writeOptional(_name, _data, i);
    i += Borsh.writeOptional(_uri, _data, i);
    i += Borsh.writeOptional(_openfundsUri, _data, i);
    i += Borsh.writeOptional(isEnabled, _data, i);
    i += Borsh.write(assets, _data, i);
    i += Borsh.write(assetsWeights, _data, i);
    i += Borsh.write(shareClasses, _data, i);
    i += Borsh.writeOptional(company, _data, i);
    i += Borsh.writeOptional(manager, _data, i);
    i += Borsh.writeOptional(created, _data, i);
    i += Borsh.write(delegateAcls, _data, i);
    i += Borsh.write(integrationAcls, _data, i);
    i += Borsh.writeOptional(isRawOpenfunds, _data, i);
    i += Borsh.writeOptional(rawOpenfunds, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.lenOptional(id, 32)
         + Borsh.lenOptional(_name)
         + Borsh.lenOptional(_uri)
         + Borsh.lenOptional(_openfundsUri)
         + (isEnabled == null ? 1 : 2)
         + Borsh.len(assets)
         + Borsh.len(assetsWeights)
         + Borsh.len(shareClasses)
         + Borsh.lenOptional(company)
         + Borsh.lenOptional(manager)
         + Borsh.lenOptional(created)
         + Borsh.len(delegateAcls)
         + Borsh.len(integrationAcls)
         + (isRawOpenfunds == null ? 1 : 2)
         + Borsh.lenOptional(rawOpenfunds);
  }
}
