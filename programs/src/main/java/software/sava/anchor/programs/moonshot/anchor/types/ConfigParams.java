package software.sava.anchor.programs.moonshot.anchor.types;

import java.util.OptionalInt;
import java.util.OptionalLong;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;

public record ConfigParams(PublicKey migrationAuthority,
                           PublicKey backendAuthority,
                           PublicKey configAuthority,
                           PublicKey helioFee,
                           PublicKey dexFee,
                           OptionalInt feeBps,
                           OptionalInt dexFeeShare,
                           OptionalLong migrationFee,
                           OptionalLong marketcapThreshold,
                           OptionalInt marketcapCurrency,
                           OptionalInt minSupportedDecimalPlaces,
                           OptionalInt maxSupportedDecimalPlaces,
                           OptionalLong minSupportedTokenSupply,
                           OptionalLong maxSupportedTokenSupply,
                           OptionalInt coefB) implements Borsh {

  public static ConfigParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var migrationAuthority = _data[i++] == 0 ? null : readPubKey(_data, i);
    if (migrationAuthority != null) {
      i += 32;
    }
    final var backendAuthority = _data[i++] == 0 ? null : readPubKey(_data, i);
    if (backendAuthority != null) {
      i += 32;
    }
    final var configAuthority = _data[i++] == 0 ? null : readPubKey(_data, i);
    if (configAuthority != null) {
      i += 32;
    }
    final var helioFee = _data[i++] == 0 ? null : readPubKey(_data, i);
    if (helioFee != null) {
      i += 32;
    }
    final var dexFee = _data[i++] == 0 ? null : readPubKey(_data, i);
    if (dexFee != null) {
      i += 32;
    }
    final var feeBps = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt16LE(_data, i));
    if (feeBps.isPresent()) {
      i += 2;
    }
    final var dexFeeShare = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(_data[i] & 0xFF);
    if (dexFeeShare.isPresent()) {
      ++i;
    }
    final var migrationFee = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (migrationFee.isPresent()) {
      i += 8;
    }
    final var marketcapThreshold = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (marketcapThreshold.isPresent()) {
      i += 8;
    }
    final var marketcapCurrency = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(_data[i] & 0xFF);
    if (marketcapCurrency.isPresent()) {
      ++i;
    }
    final var minSupportedDecimalPlaces = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(_data[i] & 0xFF);
    if (minSupportedDecimalPlaces.isPresent()) {
      ++i;
    }
    final var maxSupportedDecimalPlaces = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(_data[i] & 0xFF);
    if (maxSupportedDecimalPlaces.isPresent()) {
      ++i;
    }
    final var minSupportedTokenSupply = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (minSupportedTokenSupply.isPresent()) {
      i += 8;
    }
    final var maxSupportedTokenSupply = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (maxSupportedTokenSupply.isPresent()) {
      i += 8;
    }
    final var coefB = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt32LE(_data, i));
    return new ConfigParams(migrationAuthority,
                            backendAuthority,
                            configAuthority,
                            helioFee,
                            dexFee,
                            feeBps,
                            dexFeeShare,
                            migrationFee,
                            marketcapThreshold,
                            marketcapCurrency,
                            minSupportedDecimalPlaces,
                            maxSupportedDecimalPlaces,
                            minSupportedTokenSupply,
                            maxSupportedTokenSupply,
                            coefB);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeOptional(migrationAuthority, _data, i);
    i += Borsh.writeOptional(backendAuthority, _data, i);
    i += Borsh.writeOptional(configAuthority, _data, i);
    i += Borsh.writeOptional(helioFee, _data, i);
    i += Borsh.writeOptional(dexFee, _data, i);
    i += Borsh.writeOptionalshort(feeBps, _data, i);
    i += Borsh.writeOptionalbyte(dexFeeShare, _data, i);
    i += Borsh.writeOptional(migrationFee, _data, i);
    i += Borsh.writeOptional(marketcapThreshold, _data, i);
    i += Borsh.writeOptionalbyte(marketcapCurrency, _data, i);
    i += Borsh.writeOptionalbyte(minSupportedDecimalPlaces, _data, i);
    i += Borsh.writeOptionalbyte(maxSupportedDecimalPlaces, _data, i);
    i += Borsh.writeOptional(minSupportedTokenSupply, _data, i);
    i += Borsh.writeOptional(maxSupportedTokenSupply, _data, i);
    i += Borsh.writeOptional(coefB, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return (migrationAuthority == null ? 1 : (1 + 32))
         + (backendAuthority == null ? 1 : (1 + 32))
         + (configAuthority == null ? 1 : (1 + 32))
         + (helioFee == null ? 1 : (1 + 32))
         + (dexFee == null ? 1 : (1 + 32))
         + (feeBps == null || feeBps.isEmpty() ? 1 : (1 + 2))
         + (dexFeeShare == null || dexFeeShare.isEmpty() ? 1 : (1 + 1))
         + (migrationFee == null || migrationFee.isEmpty() ? 1 : (1 + 8))
         + (marketcapThreshold == null || marketcapThreshold.isEmpty() ? 1 : (1 + 8))
         + (marketcapCurrency == null || marketcapCurrency.isEmpty() ? 1 : (1 + 1))
         + (minSupportedDecimalPlaces == null || minSupportedDecimalPlaces.isEmpty() ? 1 : (1 + 1))
         + (maxSupportedDecimalPlaces == null || maxSupportedDecimalPlaces.isEmpty() ? 1 : (1 + 1))
         + (minSupportedTokenSupply == null || minSupportedTokenSupply.isEmpty() ? 1 : (1 + 8))
         + (maxSupportedTokenSupply == null || maxSupportedTokenSupply.isEmpty() ? 1 : (1 + 8))
         + (coefB == null || coefB.isEmpty() ? 1 : (1 + 4));
  }
}
