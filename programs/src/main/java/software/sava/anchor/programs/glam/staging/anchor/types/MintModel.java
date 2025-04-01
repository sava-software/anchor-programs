package software.sava.anchor.programs.glam.staging.anchor.types;

import java.lang.Boolean;
import java.lang.String;

import java.util.OptionalInt;
import java.util.OptionalLong;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;

public record MintModel(String symbol, byte[] _symbol,
                        String name, byte[] _name,
                        String uri, byte[] _uri,
                        PublicKey statePubkey,
                        PublicKey asset,
                        String imageUri, byte[] _imageUri,
                        PublicKey[] allowlist,
                        PublicKey[] blocklist,
                        OptionalInt lockUpPeriodInSeconds,
                        PublicKey permanentDelegate,
                        Boolean defaultAccountStateFrozen,
                        FeeStructure feeStructure,
                        FeeParams feeParams,
                        Valuation valuation,
                        OptionalLong minSubscription,
                        OptionalLong minRedemption,
                        Boolean isRawOpenfunds,
                        MintOpenfundsModel rawOpenfunds) implements Borsh {

  public static MintModel createRecord(final String symbol,
                                       final String name,
                                       final String uri,
                                       final PublicKey statePubkey,
                                       final PublicKey asset,
                                       final String imageUri,
                                       final PublicKey[] allowlist,
                                       final PublicKey[] blocklist,
                                       final OptionalInt lockUpPeriodInSeconds,
                                       final PublicKey permanentDelegate,
                                       final Boolean defaultAccountStateFrozen,
                                       final FeeStructure feeStructure,
                                       final FeeParams feeParams,
                                       final Valuation valuation,
                                       final OptionalLong minSubscription,
                                       final OptionalLong minRedemption,
                                       final Boolean isRawOpenfunds,
                                       final MintOpenfundsModel rawOpenfunds) {
    return new MintModel(symbol, Borsh.getBytes(symbol),
                         name, Borsh.getBytes(name),
                         uri, Borsh.getBytes(uri),
                         statePubkey,
                         asset,
                         imageUri, Borsh.getBytes(imageUri),
                         allowlist,
                         blocklist,
                         lockUpPeriodInSeconds,
                         permanentDelegate,
                         defaultAccountStateFrozen,
                         feeStructure,
                         feeParams,
                         valuation,
                         minSubscription,
                         minRedemption,
                         isRawOpenfunds,
                         rawOpenfunds);
  }

  public static MintModel read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var symbol = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (symbol != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var name = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (name != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var uri = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (uri != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var statePubkey = _data[i++] == 0 ? null : readPubKey(_data, i);
    if (statePubkey != null) {
      i += 32;
    }
    final var asset = _data[i++] == 0 ? null : readPubKey(_data, i);
    if (asset != null) {
      i += 32;
    }
    final var imageUri = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (imageUri != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var allowlist = _data[i++] == 0 ? null : Borsh.readPublicKeyVector(_data, i);
    if (allowlist != null) {
      i += Borsh.lenVector(allowlist);
    }
    final var blocklist = _data[i++] == 0 ? null : Borsh.readPublicKeyVector(_data, i);
    if (blocklist != null) {
      i += Borsh.lenVector(blocklist);
    }
    final var lockUpPeriodInSeconds = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt32LE(_data, i));
    if (lockUpPeriodInSeconds.isPresent()) {
      i += 4;
    }
    final var permanentDelegate = _data[i++] == 0 ? null : readPubKey(_data, i);
    if (permanentDelegate != null) {
      i += 32;
    }
    final var defaultAccountStateFrozen = _data[i++] == 0 ? null : _data[i] == 1;
    if (defaultAccountStateFrozen != null) {
      ++i;
    }
    final var feeStructure = _data[i++] == 0 ? null : FeeStructure.read(_data, i);
    if (feeStructure != null) {
      i += Borsh.len(feeStructure);
    }
    final var feeParams = _data[i++] == 0 ? null : FeeParams.read(_data, i);
    if (feeParams != null) {
      i += Borsh.len(feeParams);
    }
    final var valuation = _data[i++] == 0 ? null : Valuation.read(_data, i);
    if (valuation != null) {
      i += Borsh.len(valuation);
    }
    final var minSubscription = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (minSubscription.isPresent()) {
      i += 8;
    }
    final var minRedemption = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (minRedemption.isPresent()) {
      i += 8;
    }
    final var isRawOpenfunds = _data[i++] == 0 ? null : _data[i] == 1;
    if (isRawOpenfunds != null) {
      ++i;
    }
    final var rawOpenfunds = _data[i++] == 0 ? null : MintOpenfundsModel.read(_data, i);
    return new MintModel(symbol, Borsh.getBytes(symbol),
                         name, Borsh.getBytes(name),
                         uri, Borsh.getBytes(uri),
                         statePubkey,
                         asset,
                         imageUri, Borsh.getBytes(imageUri),
                         allowlist,
                         blocklist,
                         lockUpPeriodInSeconds,
                         permanentDelegate,
                         defaultAccountStateFrozen,
                         feeStructure,
                         feeParams,
                         valuation,
                         minSubscription,
                         minRedemption,
                         isRawOpenfunds,
                         rawOpenfunds);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeOptionalVector(_symbol, _data, i);
    i += Borsh.writeOptionalVector(_name, _data, i);
    i += Borsh.writeOptionalVector(_uri, _data, i);
    i += Borsh.writeOptional(statePubkey, _data, i);
    i += Borsh.writeOptional(asset, _data, i);
    i += Borsh.writeOptionalVector(_imageUri, _data, i);
    if (allowlist == null || allowlist.length == 0) {
      _data[i++] = 0;
    } else {
      _data[i++] = 1;
      i += Borsh.writeVector(allowlist, _data, i);
    }
    if (blocklist == null || blocklist.length == 0) {
      _data[i++] = 0;
    } else {
      _data[i++] = 1;
      i += Borsh.writeVector(blocklist, _data, i);
    }
    i += Borsh.writeOptional(lockUpPeriodInSeconds, _data, i);
    i += Borsh.writeOptional(permanentDelegate, _data, i);
    i += Borsh.writeOptional(defaultAccountStateFrozen, _data, i);
    i += Borsh.writeOptional(feeStructure, _data, i);
    i += Borsh.writeOptional(feeParams, _data, i);
    i += Borsh.writeOptional(valuation, _data, i);
    i += Borsh.writeOptional(minSubscription, _data, i);
    i += Borsh.writeOptional(minRedemption, _data, i);
    i += Borsh.writeOptional(isRawOpenfunds, _data, i);
    i += Borsh.writeOptional(rawOpenfunds, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return (_symbol == null || _symbol.length == 0 ? 1 : (1 + Borsh.lenVector(_symbol)))
         + (_name == null || _name.length == 0 ? 1 : (1 + Borsh.lenVector(_name)))
         + (_uri == null || _uri.length == 0 ? 1 : (1 + Borsh.lenVector(_uri)))
         + (statePubkey == null ? 1 : (1 + 32))
         + (asset == null ? 1 : (1 + 32))
         + (_imageUri == null || _imageUri.length == 0 ? 1 : (1 + Borsh.lenVector(_imageUri)))
         + (allowlist == null || allowlist.length == 0 ? 1 : (1 + Borsh.lenVector(allowlist)))
         + (blocklist == null || blocklist.length == 0 ? 1 : (1 + Borsh.lenVector(blocklist)))
         + (lockUpPeriodInSeconds == null || lockUpPeriodInSeconds.isEmpty() ? 1 : (1 + 4))
         + (permanentDelegate == null ? 1 : (1 + 32))
         + (defaultAccountStateFrozen == null ? 1 : (1 + 1))
         + (feeStructure == null ? 1 : (1 + Borsh.len(feeStructure)))
         + (feeParams == null ? 1 : (1 + Borsh.len(feeParams)))
         + (valuation == null ? 1 : (1 + Borsh.len(valuation)))
         + (minSubscription == null || minSubscription.isEmpty() ? 1 : (1 + 8))
         + (minRedemption == null || minRedemption.isEmpty() ? 1 : (1 + 8))
         + (isRawOpenfunds == null ? 1 : (1 + 1))
         + (rawOpenfunds == null ? 1 : (1 + Borsh.len(rawOpenfunds)));
  }
}
