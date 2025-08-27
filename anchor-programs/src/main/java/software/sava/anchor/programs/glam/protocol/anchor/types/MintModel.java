package software.sava.anchor.programs.glam.protocol.anchor.types;

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
                        OptionalInt lockUpPeriod,
                        OptionalInt yearInSeconds,
                        PublicKey permanentDelegate,
                        Boolean defaultAccountStateFrozen,
                        FeeStructure feeStructure,
                        NotifyAndSettle notifyAndSettle,
                        OptionalLong maxCap,
                        OptionalLong minSubscription,
                        OptionalLong minRedemption,
                        Boolean subscriptionPaused,
                        Boolean redemptionPaused,
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
                                       final OptionalInt lockUpPeriod,
                                       final OptionalInt yearInSeconds,
                                       final PublicKey permanentDelegate,
                                       final Boolean defaultAccountStateFrozen,
                                       final FeeStructure feeStructure,
                                       final NotifyAndSettle notifyAndSettle,
                                       final OptionalLong maxCap,
                                       final OptionalLong minSubscription,
                                       final OptionalLong minRedemption,
                                       final Boolean subscriptionPaused,
                                       final Boolean redemptionPaused,
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
                         lockUpPeriod,
                         yearInSeconds,
                         permanentDelegate,
                         defaultAccountStateFrozen,
                         feeStructure,
                         notifyAndSettle,
                         maxCap,
                         minSubscription,
                         minRedemption,
                         subscriptionPaused,
                         redemptionPaused,
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
    final var lockUpPeriod = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt32LE(_data, i));
    if (lockUpPeriod.isPresent()) {
      i += 4;
    }
    final var yearInSeconds = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt32LE(_data, i));
    if (yearInSeconds.isPresent()) {
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
    final var notifyAndSettle = _data[i++] == 0 ? null : NotifyAndSettle.read(_data, i);
    if (notifyAndSettle != null) {
      i += Borsh.len(notifyAndSettle);
    }
    final var maxCap = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (maxCap.isPresent()) {
      i += 8;
    }
    final var minSubscription = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (minSubscription.isPresent()) {
      i += 8;
    }
    final var minRedemption = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (minRedemption.isPresent()) {
      i += 8;
    }
    final var subscriptionPaused = _data[i++] == 0 ? null : _data[i] == 1;
    if (subscriptionPaused != null) {
      ++i;
    }
    final var redemptionPaused = _data[i++] == 0 ? null : _data[i] == 1;
    if (redemptionPaused != null) {
      ++i;
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
                         lockUpPeriod,
                         yearInSeconds,
                         permanentDelegate,
                         defaultAccountStateFrozen,
                         feeStructure,
                         notifyAndSettle,
                         maxCap,
                         minSubscription,
                         minRedemption,
                         subscriptionPaused,
                         redemptionPaused,
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
    i += Borsh.writeOptional(lockUpPeriod, _data, i);
    i += Borsh.writeOptional(yearInSeconds, _data, i);
    i += Borsh.writeOptional(permanentDelegate, _data, i);
    i += Borsh.writeOptional(defaultAccountStateFrozen, _data, i);
    i += Borsh.writeOptional(feeStructure, _data, i);
    i += Borsh.writeOptional(notifyAndSettle, _data, i);
    i += Borsh.writeOptional(maxCap, _data, i);
    i += Borsh.writeOptional(minSubscription, _data, i);
    i += Borsh.writeOptional(minRedemption, _data, i);
    i += Borsh.writeOptional(subscriptionPaused, _data, i);
    i += Borsh.writeOptional(redemptionPaused, _data, i);
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
         + (lockUpPeriod == null || lockUpPeriod.isEmpty() ? 1 : (1 + 4))
         + (yearInSeconds == null || yearInSeconds.isEmpty() ? 1 : (1 + 4))
         + (permanentDelegate == null ? 1 : (1 + 32))
         + (defaultAccountStateFrozen == null ? 1 : (1 + 1))
         + (feeStructure == null ? 1 : (1 + Borsh.len(feeStructure)))
         + (notifyAndSettle == null ? 1 : (1 + Borsh.len(notifyAndSettle)))
         + (maxCap == null || maxCap.isEmpty() ? 1 : (1 + 8))
         + (minSubscription == null || minSubscription.isEmpty() ? 1 : (1 + 8))
         + (minRedemption == null || minRedemption.isEmpty() ? 1 : (1 + 8))
         + (subscriptionPaused == null ? 1 : (1 + 1))
         + (redemptionPaused == null ? 1 : (1 + 1))
         + (isRawOpenfunds == null ? 1 : (1 + 1))
         + (rawOpenfunds == null ? 1 : (1 + Borsh.len(rawOpenfunds)));
  }
}
