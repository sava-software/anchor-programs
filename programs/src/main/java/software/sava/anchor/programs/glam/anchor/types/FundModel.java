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
                        PublicKey[] externalTreasuryAccounts,
                        ShareClassModel[] shareClasses,
                        CompanyModel company,
                        ManagerModel manager,
                        CreatedModel created,
                        DelegateAcl[] delegateAcls,
                        IntegrationAcl[] integrationAcls,
                        int[] driftMarketIndexesPerp,
                        int[] driftMarketIndexesSpot,
                        int[] driftOrderTypes,
                        boolean isRawOpenfunds,
                        FundOpenfundsModel rawOpenfunds) implements Borsh {

  public static FundModel createRecord(final PublicKey id,
                                       final String name,
                                       final String uri,
                                       final String openfundsUri,
                                       final Boolean isEnabled,
                                       final PublicKey[] assets,
                                       final int[] assetsWeights,
                                       final PublicKey[] externalTreasuryAccounts,
                                       final ShareClassModel[] shareClasses,
                                       final CompanyModel company,
                                       final ManagerModel manager,
                                       final CreatedModel created,
                                       final DelegateAcl[] delegateAcls,
                                       final IntegrationAcl[] integrationAcls,
                                       final int[] driftMarketIndexesPerp,
                                       final int[] driftMarketIndexesSpot,
                                       final int[] driftOrderTypes,
                                       final boolean isRawOpenfunds,
                                       final FundOpenfundsModel rawOpenfunds) {
    return new FundModel(id,
                         name, Borsh.getBytes(name),
                         uri, Borsh.getBytes(uri),
                         openfundsUri, Borsh.getBytes(openfundsUri),
                         isEnabled,
                         assets,
                         assetsWeights,
                         externalTreasuryAccounts,
                         shareClasses,
                         company,
                         manager,
                         created,
                         delegateAcls,
                         integrationAcls,
                         driftMarketIndexesPerp,
                         driftMarketIndexesSpot,
                         driftOrderTypes,
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
    i += Borsh.lenVector(assets);
    final var assetsWeights = Borsh.readintVector(_data, i);
    i += Borsh.lenVector(assetsWeights);
    final var externalTreasuryAccounts = Borsh.readPublicKeyVector(_data, i);
    i += Borsh.lenVector(externalTreasuryAccounts);
    final var shareClasses = Borsh.readVector(ShareClassModel.class, ShareClassModel::read, _data, i);
    i += Borsh.lenVector(shareClasses);
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
    i += Borsh.lenVector(delegateAcls);
    final var integrationAcls = Borsh.readVector(IntegrationAcl.class, IntegrationAcl::read, _data, i);
    i += Borsh.lenVector(integrationAcls);
    final var driftMarketIndexesPerp = Borsh.readintVector(_data, i);
    i += Borsh.lenVector(driftMarketIndexesPerp);
    final var driftMarketIndexesSpot = Borsh.readintVector(_data, i);
    i += Borsh.lenVector(driftMarketIndexesSpot);
    final var driftOrderTypes = Borsh.readintVector(_data, i);
    i += Borsh.lenVector(driftOrderTypes);
    final var isRawOpenfunds = _data[i] == 1;
    ++i;
    final var rawOpenfunds = _data[i++] == 0 ? null : FundOpenfundsModel.read(_data, i);
    return new FundModel(id,
                         name, Borsh.getBytes(name),
                         uri, Borsh.getBytes(uri),
                         openfundsUri, Borsh.getBytes(openfundsUri),
                         isEnabled,
                         assets,
                         assetsWeights,
                         externalTreasuryAccounts,
                         shareClasses,
                         company,
                         manager,
                         created,
                         delegateAcls,
                         integrationAcls,
                         driftMarketIndexesPerp,
                         driftMarketIndexesSpot,
                         driftOrderTypes,
                         isRawOpenfunds,
                         rawOpenfunds);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeOptional(id, _data, i);
    i += Borsh.writeOptionalVector(_name, _data, i);
    i += Borsh.writeOptionalVector(_uri, _data, i);
    i += Borsh.writeOptionalVector(_openfundsUri, _data, i);
    i += Borsh.writeOptional(isEnabled, _data, i);
    i += Borsh.writeVector(assets, _data, i);
    i += Borsh.writeVector(assetsWeights, _data, i);
    i += Borsh.writeVector(externalTreasuryAccounts, _data, i);
    i += Borsh.writeVector(shareClasses, _data, i);
    i += Borsh.writeOptional(company, _data, i);
    i += Borsh.writeOptional(manager, _data, i);
    i += Borsh.writeOptional(created, _data, i);
    i += Borsh.writeVector(delegateAcls, _data, i);
    i += Borsh.writeVector(integrationAcls, _data, i);
    i += Borsh.writeVector(driftMarketIndexesPerp, _data, i);
    i += Borsh.writeVector(driftMarketIndexesSpot, _data, i);
    i += Borsh.writeVector(driftOrderTypes, _data, i);
    _data[i] = (byte) (isRawOpenfunds ? 1 : 0);
    ++i;
    i += Borsh.writeOptional(rawOpenfunds, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return (id == null ? 1 : (1 + 32))
         + (_name == null || _name.length == 0 ? 1 : (1 + Borsh.lenVector(_name)))
         + (_uri == null || _uri.length == 0 ? 1 : (1 + Borsh.lenVector(_uri)))
         + (_openfundsUri == null || _openfundsUri.length == 0 ? 1 : (1 + Borsh.lenVector(_openfundsUri)))
         + (isEnabled == null ? 1 : (1 + 1))
         + Borsh.lenVector(assets)
         + Borsh.lenVector(assetsWeights)
         + Borsh.lenVector(externalTreasuryAccounts)
         + Borsh.lenVector(shareClasses)
         + (company == null ? 1 : (1 + Borsh.len(company)))
         + (manager == null ? 1 : (1 + Borsh.len(manager)))
         + (created == null ? 1 : (1 + Borsh.len(created)))
         + Borsh.lenVector(delegateAcls)
         + Borsh.lenVector(integrationAcls)
         + Borsh.lenVector(driftMarketIndexesPerp)
         + Borsh.lenVector(driftMarketIndexesSpot)
         + Borsh.lenVector(driftOrderTypes)
         + 1
         + (rawOpenfunds == null ? 1 : (1 + Borsh.len(rawOpenfunds)));
  }
}
